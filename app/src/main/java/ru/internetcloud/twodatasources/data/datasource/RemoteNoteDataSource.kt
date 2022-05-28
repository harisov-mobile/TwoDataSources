package ru.internetcloud.twodatasources.data.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import javax.inject.Inject
import ru.internetcloud.twodatasources.data.database.AppDao2
import ru.internetcloud.twodatasources.data.mapper.NoteMapper
import ru.internetcloud.twodatasources.domain.model.Note

class RemoteNoteDataSource @Inject constructor(
    private val appDao2: AppDao2,
    private val noteMapper: NoteMapper
) {

    fun getAllNotes(): LiveData<List<Note>> {
        return Transformations.map(appDao2.getAllNotes()) {
            noteMapper.fromListDbModelToListEntity(it)
        }
    }

    suspend fun insertNote(note: Note) {
        appDao2.insertNote(noteMapper.fromEntityToDbModel(note))
    }

    suspend fun deleteNote(note: Note) {
        appDao2.deleteNote(noteMapper.fromEntityToDbModel(note))
    }

    suspend fun getNoteById(id: Int): Note? {
        val noteDbModel = appDao2.getNoteById(id)

        return noteDbModel?.let {
            noteMapper.fromDbModelToEntity(it)
        } ?: let {
            null
        }
    }
}
