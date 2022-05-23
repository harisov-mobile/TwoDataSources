package ru.internetcloud.twodatasources.presentation.note_list

import androidx.lifecycle.ViewModel
import javax.inject.Inject
import ru.internetcloud.twodatasources.domain.model.DataSourceType
import ru.internetcloud.twodatasources.domain.usecase.GetAllNotesUseCase

class NoteListViewModel @Inject constructor(
    private val getAllNotesUseCase: GetAllNotesUseCase
) : ViewModel() {

    val noteListLiveData = getAllNotesUseCase.getAllNotes(DataSourceType.ROOM_DATABASE1)
}
