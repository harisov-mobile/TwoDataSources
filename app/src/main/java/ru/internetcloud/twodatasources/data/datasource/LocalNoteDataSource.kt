package ru.internetcloud.twodatasources.data.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import javax.inject.Inject
import ru.internetcloud.twodatasources.data.database.AppDao
import ru.internetcloud.twodatasources.data.mapper.NoteMapper
import ru.internetcloud.twodatasources.domain.model.Note

class LocalNoteDataSource @Inject constructor(
    private val appDao: AppDao,
    private val noteMapper: NoteMapper
) {

    fun getAllNotes(): LiveData<List<Note>> {
        return Transformations.map(appDao.getAllNotes()) {
            noteMapper.fromListDbModelToListEntity(it)
        }
    }

    // вариант с DataResult
//    fun getAllWords(): DataResult<LiveData<List<Word>>> {
//
//        var dataResult: DataResult<LiveData<List<Word>>>
//
//        try {
//            val liveData = Transformations.map(appDao.getAllWords()) {
//                wordMapper.fromListDbModelToListEntity(it)
//            }
//            dataResult = DataResult.Success(liveData)
//        } catch (e: Exception) {
//            dataResult = DataResult.Error(data = null, e.message.toString())
//        }
//
//        return dataResult
//    }

    suspend fun insertNote(note: Note) {
        appDao.insertNote(noteMapper.fromEntityToDbModel(note))
    }

    suspend fun deleteNote(note: Note) {
        appDao.deleteNote(noteMapper.fromEntityToDbModel(note))
    }

    suspend fun getNoteById(id: Int): Note? {
        val noteDbModel = appDao.getNoteById(id)

        return noteDbModel?.let {
            noteMapper.fromDbModelToEntity(it)
        } ?: let {
            null
        }
    }
}