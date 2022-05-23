package ru.internetcloud.twodatasources.domain.usecase

import javax.inject.Inject
import ru.internetcloud.twodatasources.domain.model.DataSourceType
import ru.internetcloud.twodatasources.domain.model.Note
import ru.internetcloud.twodatasources.domain.repository.NoteRepository

class DeleteNoteUseCase @Inject constructor(private val noteRepository: NoteRepository) {

    suspend fun deleteNote(note: Note, dataSourceType: DataSourceType) {
        noteRepository.deleteNote(note, dataSourceType)
    }
}