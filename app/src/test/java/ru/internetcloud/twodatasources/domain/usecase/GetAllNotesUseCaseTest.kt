package ru.internetcloud.twodatasources.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import ru.internetcloud.twodatasources.domain.model.DataSourceType
import ru.internetcloud.twodatasources.domain.model.Note
import ru.internetcloud.twodatasources.domain.repository.NoteRepository

class GetAllNotesUseCaseTest {

    @Test
    fun method_getAllNotes_calls_repository_getAllNotes() {
        val repository = mock<NoteRepository>()
        val getAllNotesUseCase = GetAllNotesUseCase(repository)

        getAllNotesUseCase.getAllNotes(DataSourceType.ROOM_DATABASE1)

        verify(repository, times(1)).getAllNotes(any())
    }

    @Test
    fun method_getAllNotes_returns_what_repository_gets() {
        val repository = mock<NoteRepository>()
        val getAllNotesUseCase = GetAllNotesUseCase(repository)

        val list: List<Note> = mutableListOf(
            Note(
                id = 0,
                name = "Name1",
                text = "text1"
            ),
            Note(
                id = 1,
                name = "Name2",
                text = "text2"
            )
        )

        val expected: LiveData<List<Note>> = MutableLiveData<List<Note>>(list)

        whenever(repository.getAllNotes(any())).thenReturn(expected)

        val actual = getAllNotesUseCase.getAllNotes(DataSourceType.ROOM_DATABASE1)

        assertEquals(expected, actual)
    }
}
