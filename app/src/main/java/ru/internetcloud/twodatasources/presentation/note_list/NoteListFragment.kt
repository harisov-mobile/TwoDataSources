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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import java.lang.IllegalStateException
import javax.inject.Inject
import ru.internetcloud.twodatasources.R
import ru.internetcloud.twodatasources.TwoDataSourcesApp
import ru.internetcloud.twodatasources.databinding.FragmentNoteListBinding
import ru.internetcloud.twodatasources.di.ViewModelFactory
import ru.internetcloud.twodatasources.domain.model.OperationMode

class NoteListFragment : Fragment() {

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
        viewModel.noteListLiveData.observe(viewLifecycleOwner) { list ->
            noteListAdapter.submitList(list)
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

        when(item.itemId) {
            R.id.settings_menu_item -> {
                findNavController().navigate(R.id.action_noteListFragment_to_settingsFragment)
                return true
            }

            R.id.about_menu_item -> {
                Toast.makeText(context, "Navigate to About", Toast.LENGTH_SHORT).show()
                return true
            }

            else ->  return super.onOptionsItemSelected(item)
        }
    }
}
