package ru.internetcloud.twodatasources.presentation.note_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.internetcloud.twodatasources.databinding.ItemNoteListBinding
import ru.internetcloud.twodatasources.domain.model.Note

class NoteListAdapter : ListAdapter<Note, NoteListViewHolder>(NoteDiffCallback()) {

    // для отработки нажатий на элемент списка - переменная, которая будет хранить лямбда-функцию,
    // на вход лямбда-функции в качестве параметра будет передан note: Note,
    // лямбда-функция ничего не возвращает (то есть Unit)
    // а первоначально переменная содержит null
    var onNoteListClickListener: ((note: Note) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteListViewHolder {
        val binding = ItemNoteListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteListViewHolder, position: Int) {
        val binding = holder.binding as ItemNoteListBinding

        val note = getItem(position)

        binding.itemNameTextView.text = note.name
        binding.itemTextTextView.text = note.text

        binding.root.setOnClickListener {
            onNoteListClickListener?.invoke(note)
        }
    }
}
