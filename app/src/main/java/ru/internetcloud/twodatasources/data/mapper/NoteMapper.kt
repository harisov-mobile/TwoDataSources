package ru.internetcloud.twodatasources.data.mapper

import javax.inject.Inject
import ru.internetcloud.twodatasources.data.entity.NoteDbModel
import ru.internetcloud.twodatasources.domain.model.Note

class NoteMapper @Inject constructor() {

    fun fromEntityToDbModel(note: Note): NoteDbModel {
        return NoteDbModel(
            id = note.id,
            name = note.name,
            text = note.text
        )
    }

    fun fromDbModelToEntity(noteDbModel: NoteDbModel): Note {
        return Note(
            id = noteDbModel.id,
            name = noteDbModel.name,
            text = noteDbModel.text
        )
    }

    fun fromListDbModelToListEntity(list: List<NoteDbModel>) = list.map {
        fromDbModelToEntity(it)
    }
}
