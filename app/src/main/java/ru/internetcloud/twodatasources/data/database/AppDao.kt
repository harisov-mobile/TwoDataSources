package ru.internetcloud.twodatasources.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.internetcloud.twodatasources.data.entity.NoteDbModel

@Dao
interface AppDao {

    @Query("SELECT * FROM notes")
    fun getAllNotes(): LiveData<List<NoteDbModel>>

    @Query("SELECT * FROM notes WHERE id=:noteId LIMIT 1")
    suspend fun getNoteById(noteId: Int): NoteDbModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(noteDbModel: NoteDbModel)

//    @Query("DELETE FROM notes WHERE id=:noteId")
//    suspend fun deleteNote(noteId: Int)

    @Delete
    suspend fun deleteNote(noteDbModel: NoteDbModel)
}
