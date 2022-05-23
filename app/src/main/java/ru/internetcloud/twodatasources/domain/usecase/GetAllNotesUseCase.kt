package ru.internetcloud.twodatasources.domain.usecase

import androidx.lifecycle.LiveData
import javax.inject.Inject
import ru.internetcloud.twodatasources.domain.model.DataSourceType
import ru.internetcloud.twodatasources.domain.model.Note
import ru.internetcloud.twodatasources.domain.repository.NoteRepository

class GetAllNotesUseCase @Inject constructor(private val noteRepository: NoteRepository) {

    fun getAllNotes(dataSourceType: DataSourceType): LiveData<List<Note>> {
        return noteRepository.getAllNotes(dataSourceType)
    }
}
