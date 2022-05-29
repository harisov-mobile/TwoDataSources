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

    var flowDataSourceTypeLiveData: LiveData<DataSourceType>? = null

    fun loadNotes() {

        // TODO Как избежать статического класса (объекта) currentDataSourceType?
        // нужен элегантный способ считать DataSourceType из Хранилища
        // и использовать его уже не прибегая к хранилищу!

        if (flowDataSourceTypeLiveData == null || currentDataSourceType.getCurrent() == null) {
            val flowDataSourceType = readDataSourceTypeUseCase.readDataSourceType()
            flowDataSourceTypeLiveData = flowDataSourceType.asLiveData()
            flowDataSourceTypeLiveData?.observeForever(flowDataSourceTypeObserver)
        } else {
            currentDataSourceType.getCurrent()?.let { dataSourceType ->
                noteListLiveData = getAllNotesUseCase.getAllNotes(dataSourceType)
                _dataIsLoaded.value = true
            }
        }
    }

    override fun onCleared() {
        super.onCleared()

        // статическая переменная currentDataSourceType при выходе из приложения
        // почему-то не обнуляется!
        flowDataSourceTypeLiveData?.removeObserver(flowDataSourceTypeObserver)
    }
}
