package ru.internetcloud.twodatasources.presentation.note_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import java.lang.IllegalStateException
import ru.internetcloud.twodatasources.databinding.FragmentNoteListBinding

class NoteListFragment : Fragment() {

    private var _binding: FragmentNoteListBinding? = null
            val binding: FragmentNoteListBinding
            get() = _binding ?: throw IllegalStateException("FragmentNoteListBinding is null")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentNoteListBinding.inflate(inflater, container, false)
        return binding.root
    }
}