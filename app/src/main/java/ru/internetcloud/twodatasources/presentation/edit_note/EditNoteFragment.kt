package ru.internetcloud.twodatasources.presentation.edit_note

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import javax.inject.Inject
import ru.internetcloud.twodatasources.R
import ru.internetcloud.twodatasources.TwoDataSourcesApp
import ru.internetcloud.twodatasources.databinding.FragmentEditNoteBinding
import ru.internetcloud.twodatasources.di.ViewModelFactory
import ru.internetcloud.twodatasources.domain.model.Note
import ru.internetcloud.twodatasources.domain.model.OperationMode
import ru.internetcloud.twodatasources.presentation.dialog.MessageDialogFragment
import ru.internetcloud.twodatasources.presentation.dialog.QuestionDialogFragment

class EditNoteFragment : Fragment(), FragmentResultListener {

    // даггер:
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as TwoDataSourcesApp).component
    }

    private var _binding: FragmentEditNoteBinding? = null
    val binding: FragmentEditNoteBinding
    get() = _binding ?: throw IllegalStateException("FragmentEditNoteBinding is null")

    private lateinit var viewModel: EditNoteViewModel

    private val args: EditNoteFragmentArgs by navArgs()

    private val REQUEST_DATA_WAS_CHANGED_KEY = "data_was_changed_key"
    private val REQUEST_DELETE_NOTE_KEY = "delete_note_key"
    private val ARG_ANSWER = "answer"

    override fun onAttach(context: Context) {
        super.onAttach(context)

        component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(EditNoteViewModel::class.java)

        viewModel.note?: let {
            // это новый экран, т.к. viewModel.note не проинициализирована
            readArgs()
        }

        viewModel.note?.let { currentNote ->
            updateUI(currentNote)
        }

        observeViewModel()
        setupClickListeners()
        setupMenu()

        childFragmentManager.setFragmentResultListener(REQUEST_DATA_WAS_CHANGED_KEY, viewLifecycleOwner, this)
        childFragmentManager.setFragmentResultListener(REQUEST_DELETE_NOTE_KEY, viewLifecycleOwner, this)
    }

    override fun onStart() {
        super.onStart()

        // TextWatcher нужно навешивать здесь, а не в onCreate или onCreateView, т.к. там еще не восстановлено
        // EditText и слушатели будут "дергаться" лишний раз
        binding.nameTextInputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.note?.name = p0?.toString() ?: ""
                viewModel.isChanged = true
            }
        })

        binding.textTextInputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.note?.text = p0?.toString() ?: ""
                viewModel.isChanged = true
            }
        })
    }

    private fun updateUI(note: Note) {
        binding.nameTextInputEditText.setText(note.name)
        binding.textTextInputEditText.setText(note.text)
    }

    private fun readArgs() {
        viewModel.operationMode = args.operationMode
        if (viewModel.operationMode != OperationMode.EDIT && viewModel.operationMode != OperationMode.ADD) {
            throw RuntimeException("Uknown screen mode ${viewModel.operationMode}")
        }
        if (viewModel.operationMode == OperationMode.EDIT) {
            viewModel.note = args.note
        } else {
            viewModel.createNote()
        }
    }

    private fun setupMenu() {
        if (viewModel.operationMode == OperationMode.EDIT) {
            setHasOptionsMenu(true) // меню надо показывать только для режима редактирования, т.к.
            // в режиме добавления - удалять из базы еще нечего, не добавлено же еще...
        }
    }

    private fun setupClickListeners() {
        binding.saveNoteButton.setOnClickListener {
            viewModel.note?.let { currentNote ->
                viewModel.insertNote(currentNote)
            }

        }

        binding.closeNoteButton.setOnClickListener {
            onCloseNote()
        }
    }

    private fun observeViewModel() {
        // подписка на ошибки
        viewModel.errorInputName.observe(viewLifecycleOwner) { isError ->
            val message = if (isError) {
                getString(R.string.error_input_name)
            } else {
                null
            }
            binding.nameTextInputLayout.error = message
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            MessageDialogFragment.newInstance(getString(R.string.error), error)
                .show(childFragmentManager, null)
        }

        // подписка на успешное завершение сохранения
        viewModel.noteIsSaved.observe(viewLifecycleOwner) { isSaved ->
            if (isSaved) {
                if (viewModel.closeOnSave) {
                    Toast.makeText(context, getString(R.string.success_saved), Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                } else {
                    viewModel.isChanged = false
                    MessageDialogFragment.newInstance("", getString(R.string.success_saved))
                        .show(childFragmentManager, null)
                }
            }
        }

        // подписка на успешное завершение удаления
        viewModel.noteIsDeleted.observe(viewLifecycleOwner) { deleted ->
            if (deleted) {
                Toast.makeText(context, getString(R.string.success_deleted), Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }
    }

    private fun onCloseNote() {
        viewModel.note?.let { currentNote ->
            if (viewModel.isChanged) {
                QuestionDialogFragment
                    .newInstance(
                        getString(R.string.data_was_changed_question),
                        REQUEST_DATA_WAS_CHANGED_KEY,
                        ARG_ANSWER
                    )
                    .show(childFragmentManager, REQUEST_DATA_WAS_CHANGED_KEY)
            } else {
                findNavController().popBackStack()
            }
        } ?: let {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onFragmentResult(requestKey: String, result: Bundle) {
        when (requestKey) {
            REQUEST_DATA_WAS_CHANGED_KEY -> {
                val needSaveData: Boolean = result.getBoolean(ARG_ANSWER, false)
                if (needSaveData) {
                    viewModel.note?.let { currentNote ->
                        viewModel.closeOnSave = true
                        viewModel.insertNote(currentNote)
                    }
                } else {
                    findNavController().popBackStack()
                }
            }

            REQUEST_DELETE_NOTE_KEY -> {
                val needDeleteData: Boolean = result.getBoolean(ARG_ANSWER, false)
                if (needDeleteData) {
                    viewModel.note?.let { currentNote ->
                        viewModel.deleteNote(currentNote)
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.edit_note_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.delete_note_menu_item -> {
                QuestionDialogFragment
                    .newInstance(
                        getString(R.string.delete_note_question),
                        REQUEST_DELETE_NOTE_KEY,
                        ARG_ANSWER
                    )
                    .show(childFragmentManager, REQUEST_DELETE_NOTE_KEY)
                return true
            }

            R.id.settings_menu_item -> {
                findNavController().navigate(R.id.action_editNoteFragment_to_settingsFragment)
                return true
            }

            else ->
                return super.onOptionsItemSelected(item)
        }
    }

}
