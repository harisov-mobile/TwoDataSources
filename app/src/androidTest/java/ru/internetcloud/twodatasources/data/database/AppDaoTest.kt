package ru.internetcloud.twodatasources.data.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.internetcloud.twodatasources.data.entity.NoteDbModel

// @ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class AppDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var dao: AppDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.appDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertNote() = runBlockingTest {

        val newNoteDbModel = NoteDbModel(
            id = 1,
            name = "name1",
            text = "text1"
        )

        dao.insertNote(newNoteDbModel)

        val allNotesList: List<NoteDbModel> = dao.getAllNotes().getOrAwaitValue()

        val expected = true

        val actual: Boolean = allNotesList.contains(newNoteDbModel)

        assertEquals(expected, actual)
    }

    @Test
    fun deleteNote() = runBlockingTest {
        val newNoteDbModel = NoteDbModel(
            id = 1,
            name = "name1",
            text = "text1"
        )

        dao.insertNote(newNoteDbModel)
        dao.deleteNote(newNoteDbModel)

        val allNotesList: List<NoteDbModel> = dao.getAllNotes().getOrAwaitValue()

        val expected = false

        val actual: Boolean = allNotesList.contains(newNoteDbModel)

        assertEquals(expected, actual)
    }
}