package ru.internetcloud.twodatasources.presentation.note_list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import java.lang.IllegalStateException
import javax.inject.Inject
import ru.internetcloud.twodatasources.R
import ru.internetcloud.twodatasources.TwoDataSourcesApp
import ru.internetcloud.twodatasources.databinding.FragmentNoteListBinding
import ru.internetcloud.twodatasources.di.ViewModelFactory
import ru.internetcloud.twodatasources.domain.model.OperationMode
import ru.internetcloud.twodatasources.presentation.dialog.QuestionDialogFragment

class NoteListFragment : Fragment(), FragmentResultListener {

    // даггер:
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as TwoDataSourcesApp).component
    }

    private var _binding: FragmentNoteListBinding? = null
            val binding: FragmentNoteListBinding
            get() = _binding ?: throw IllegalStateException("FragmentNoteListBinding is null")

    private lateinit var viewModel: NoteListViewModel
    private lateinit var noteListAdapter: NoteListAdapter

    companion object {
        private val REQUEST_EXIT_QUESTION_KEY = "exit_question_key"
        private val ARG_ANSWER = "answer"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // даггер:
        component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentNoteListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(NoteListViewModel::class.java)

        setupNoteRecyclerView()
        observeViewModel()
        setupClickListeners()
        setHasOptionsMenu(true)

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onExitNoteList()
            }
        })

        childFragmentManager.setFragmentResultListener(REQUEST_EXIT_QUESTION_KEY, viewLifecycleOwner, this)

        savedInstanceState ?: let {
            viewModel.loadNotes()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupNoteRecyclerView() {
        noteListAdapter = NoteListAdapter()
        binding.noteListRecyclerView.adapter = noteListAdapter
        // в XML  прописано app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"
    }

    private fun observeViewModel() {
        viewModel.dataIsLoaded.observe(viewLifecycleOwner) { isLoaded ->
            if (isLoaded) {
                viewModel.noteListLiveData.observe(viewLifecycleOwner) { list ->
                    noteListAdapter.submitList(list)
                }
            }
        }
    }

    private fun setupClickListeners() {
        binding.addNoteButton.setOnClickListener {
            val operationMode = OperationMode.ADD

            val fragmentLabel = getString(R.string.add_note_fragment_label)

            val direction = NoteListFragmentDirections.actionNoteListFragmentToEditNoteFragment(
                operationMode = operationMode,
                note = null,
                fragmentEditNoteLabel = fragmentLabel)
            findNavController().navigate(direction)
        }

        noteListAdapter.onNoteListClickListener = { currentNote ->
            val operationMode = OperationMode.EDIT

            val fragmentLabel = getString(R.string.edit_note_fragment_label)

            val copyNote = currentNote.copy() // надо копию экземпляра класса, специально чтобы при изменении
            // копии не пострадал оригинал, если пользователь нажмет "Отмена"

            val direction = NoteListFragmentDirections.actionNoteListFragmentToEditNoteFragment(
                operationMode = operationMode,
                note = copyNote,
                fragmentEditNoteLabel = fragmentLabel)
            findNavController().navigate(direction)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.note_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.settings_menu_item -> {
                findNavController().navigate(R.id.action_noteListFragment_to_settingsFragment)
                return true
            }

            R.id.about_menu_item -> {
                Toast.makeText(context, "Navigate to About", Toast.LENGTH_SHORT).show()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun onExitNoteList() {
        QuestionDialogFragment
            .newInstance(
                getString(R.string.exit_from_app_question, getString(R.string.app_name)),
                REQUEST_EXIT_QUESTION_KEY,
                ARG_ANSWER
            )
            .show(childFragmentManager, REQUEST_EXIT_QUESTION_KEY)
    }

    override fun onFragmentResult(requestKey: String, result: Bundle) {
        when (requestKey) {
            REQUEST_EXIT_QUESTION_KEY -> {
                val exit: Boolean = result.getBoolean(ARG_ANSWER, false)
                if (exit) {
                    activity?.finish()
                }
            }
        }
    }
}
