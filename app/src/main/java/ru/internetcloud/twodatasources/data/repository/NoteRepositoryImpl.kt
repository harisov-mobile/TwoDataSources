package ru.internetcloud.twodatasources.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import javax.inject.Inject
import ru.internetcloud.twodatasources.data.database.AppDao
import ru.internetcloud.twodatasources.data.entity.NoteDbModel
import ru.internetcloud.twodatasources.data.mapper.NoteMapper
import ru.internetcloud.twodatasources.domain.model.DataSourceType
import ru.internetcloud.twodatasources.domain.model.Note
import ru.internetcloud.twodatasources.domain.repository.NoteRepository

class NoteRepositoryImpl @Inject constructor(
    private val appDao: AppDao,
    private val noteMapper: NoteMapper
) : NoteRepository {
    override suspend fun insertNote(note: Note, dataSourceType: DataSourceType) {
        appDao.insertNote(noteMapper.fromEntityToDbModel(note))
    }

    override suspend fun deleteNote(note: Note, dataSourceType: DataSourceType) {
        appDao.deleteNote(noteMapper.fromEntityToDbModel(note))
    }

    override suspend fun getNoteById(id: Int, dataSourceType: DataSourceType): Note? {

        var note: Note? = null

        val noteDbModel = appDao.getNoteById(id)

        noteDbModel?.let {
            note = noteMapper.fromDbModelToEntity(it)
        }

        return note
    }

    override fun getAllNotes(dataSourceType: DataSourceType): LiveData<List<Note>> {
        var ld: LiveData<List<NoteDbModel>> = appDao.getAllNotes()
        return Transformations.map(ld) {
            noteMapper.fromListDbModelToListEntity(it)
        }
    }
}
