package ru.internetcloud.twodatasources.presentation.note_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import javax.inject.Inject
import ru.internetcloud.twodatasources.domain.model.CurrentDataSourceType
import ru.internetcloud.twodatasources.domain.model.DataSourceType
import ru.internetcloud.twodatasources.domain.model.Note
import ru.internetcloud.twodatasources.domain.usecase.GetAllNotesUseCase
import ru.internetcloud.twodatasources.domain.usecase.ReadDataSourceTypeUseCase

class NoteListViewModel @Inject constructor(
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val currentDataSourceType: CurrentDataSourceType,
    private val readDataSourceTypeUseCase: ReadDataSourceTypeUseCase
) : ViewModel() {

    lateinit var noteListLiveData: LiveData<List<Note>>

    private val _dataIsLoaded = MutableLiveData<Boolean>()
    val dataIsLoaded: LiveData<Boolean>
        get() = _dataIsLoaded

    private val flowDataSourceTypeObserver = Observer<DataSourceType> { dataSourceType ->
        currentDataSourceType.setCurrent(dataSourceType)
        noteListLiveData = getAllNotesUseCase.getAllNotes(dataSourceType)
        _dataIsLoaded.value = true
    }

    private lateinit var flowDataSourceTypeLiveData: LiveData<DataSourceType>

    fun loadNotes() {
        currentDataSourceType.getCurrent()?.let { dataSourceType ->
            noteListLiveData = getAllNotesUseCase.getAllNotes(dataSourceType)
            _dataIsLoaded.value = true
        } ?: let {
            val flowDataSourceType = readDataSourceTypeUseCase.readDataSourceType()
            flowDataSourceTypeLiveData = flowDataSourceType.asLiveData()
            flowDataSourceTypeLiveData.observeForever(flowDataSourceTypeObserver)
        }
    }

    override fun onCleared() {
        super.onCleared()

        flowDataSourceTypeLiveData.removeObserver(flowDataSourceTypeObserver)
    }
}
