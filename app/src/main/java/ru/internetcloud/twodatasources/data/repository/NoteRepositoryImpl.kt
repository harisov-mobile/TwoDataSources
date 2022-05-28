package ru.internetcloud.twodatasources.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import javax.inject.Inject
import ru.internetcloud.twodatasources.data.database.AppDao
import ru.internetcloud.twodatasources.data.datasource.LocalNoteDataSource
import ru.internetcloud.twodatasources.data.datasource.RemoteNoteDataSource
import ru.internetcloud.twodatasources.data.entity.NoteDbModel
import ru.internetcloud.twodatasources.data.mapper.NoteMapper
import ru.internetcloud.twodatasources.domain.model.DataSourceType
import ru.internetcloud.twodatasources.domain.model.Note
import ru.internetcloud.twodatasources.domain.repository.NoteRepository

class NoteRepositoryImpl @Inject constructor(
    private val remoteNoteDataSource: RemoteNoteDataSource,
    private val localNoteDataSource: LocalNoteDataSource
) : NoteRepository {

    override suspend fun insertNote(note: Note, dataSourceType: DataSourceType) {
        when (dataSourceType) {
            DataSourceType.ROOM_DATABASE1 -> localNoteDataSource.insertNote(note)
            DataSourceType.ROOM_DATABASE2 -> remoteNoteDataSource.insertNote(note)
        }
    }

    override suspend fun deleteNote(note: Note, dataSourceType: DataSourceType) {
        when (dataSourceType) {
            DataSourceType.ROOM_DATABASE1 -> localNoteDataSource.deleteNote(note)
            DataSourceType.ROOM_DATABASE2 -> remoteNoteDataSource.deleteNote(note)
        }
    }

    override suspend fun getNoteById(id: Int, dataSourceType: DataSourceType): Note? {

        return when (dataSourceType) {
            DataSourceType.ROOM_DATABASE1 -> localNoteDataSource.getNoteById(id)
            DataSourceType.ROOM_DATABASE2 -> remoteNoteDataSource.getNoteById(id)
        }
    }

    override fun getAllNotes(dataSourceType: DataSourceType): LiveData<List<Note>> {

        return when (dataSourceType) {
            DataSourceType.ROOM_DATABASE1 -> localNoteDataSource.getAllNotes()
            DataSourceType.ROOM_DATABASE2 -> remoteNoteDataSource.getAllNotes()
        }
    }
}
