package ru.internetcloud.twodatasources.presentation.note_list

import androidx.recyclerview.widget.DiffUtil
import ru.internetcloud.twodatasources.domain.model.Note

class NoteDiffCallback: DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }
}