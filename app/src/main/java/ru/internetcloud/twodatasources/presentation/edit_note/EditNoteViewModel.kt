package ru.internetcloud.twodatasources.presentation.edit_note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.internetcloud.twodatasources.domain.model.DataSourceType
import ru.internetcloud.twodatasources.domain.model.Note
import ru.internetcloud.twodatasources.domain.model.OperationMode
import ru.internetcloud.twodatasources.domain.usecase.DeleteNoteUseCase
import ru.internetcloud.twodatasources.domain.usecase.InsertNoteUseCase

class EditNoteViewModel @Inject constructor(
    private val insertNoteUseCase: InsertNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {

    var note: Note? = null

    private val _noteIsSaved = MutableLiveData<Boolean>()
    val noteIsSaved: LiveData<Boolean>
        get() = _noteIsSaved

    private val _noteIsDeleted = MutableLiveData<Boolean>()
    val noteIsDeleted: LiveData<Boolean>
        get() = _noteIsDeleted

    // для обработки ошибок:
    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    var isChanged: Boolean = false
    var closeOnSave: Boolean = false
    var operationMode: OperationMode? = null

    fun createNote() {
        note = Note()
    }

    fun insertNote(updatableNote: Note) {
        val areFieldsValid = validateInput(updatableNote)
        if (areFieldsValid) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    insertNoteUseCase.insertNote(updatableNote, DataSourceType.ROOM_DATABASE1)
                    _noteIsSaved.postValue(true)
                } catch (e: Exception) {
                    _noteIsSaved.postValue(false)
                    _errorMessage.postValue(e.message.toString())
                }
            }
        }
    }

    private fun validateInput(validatableNote: Note): Boolean {
        var result = true
        if (validatableNote.name.isBlank()) {
            _errorInputName.value = true
            closeOnSave = false
            result = false
        }

        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun deleteNote(deletableNote: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                deleteNoteUseCase.deleteNote(deletableNote, DataSourceType.ROOM_DATABASE1)
                _noteIsDeleted.postValue(true)
            } catch (e: Exception) {
                _noteIsDeleted.postValue(false)
                _errorMessage.postValue(e.message.toString())
            }
        }
    }
}
