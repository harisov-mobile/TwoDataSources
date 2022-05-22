package ru.internetcloud.twodatasources.presentation.note_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import java.lang.IllegalStateException
import ru.internetcloud.twodatasources.R
import ru.internetcloud.twodatasources.databinding.FragmentNoteListBinding

class NoteListFragment : Fragment() {

    private var _binding: FragmentNoteListBinding? = null
            val binding: FragmentNoteListBinding
            get() = _binding ?: throw IllegalStateException("FragmentNoteListBinding is null")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentNoteListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.note_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.settings_menu_item -> {
                Toast.makeText(context, "Navigate to Settings", Toast.LENGTH_SHORT).show()
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