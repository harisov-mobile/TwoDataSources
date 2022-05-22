package ru.internetcloud.twodatasources.domain.repository

import androidx.lifecycle.LiveData
import ru.internetcloud.twodatasources.domain.model.DataSourceType
import ru.internetcloud.twodatasources.domain.model.Note

interface NoteRepository {

    suspend fun insertNote(note: Note, dataSourceType: DataSourceType)

    suspend fun deleteNote(note: Note, dataSourceType: DataSourceType)

    fun getNoteById(id: Int, dataSourceType: DataSourceType): LiveData<Note?>

    fun getAllNotes(dataSourceType: DataSourceType): LiveData<List<Note>>
}