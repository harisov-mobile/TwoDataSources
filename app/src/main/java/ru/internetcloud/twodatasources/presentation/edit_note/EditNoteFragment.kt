package ru.internetcloud.twodatasources.presentation.edit_note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.internetcloud.twodatasources.databinding.FragmentEditNoteBinding

class EditNoteFragment : Fragment() {

    private var _binding: FragmentEditNoteBinding? = null
    val binding: FragmentEditNoteBinding
    get() = _binding ?: throw IllegalStateException("FragmentEditNoteBinding is null")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}