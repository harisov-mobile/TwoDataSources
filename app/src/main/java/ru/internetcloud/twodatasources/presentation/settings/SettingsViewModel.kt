package ru.internetcloud.twodatasources.presentation.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.internetcloud.twodatasources.domain.model.CurrentDataSourceType
import ru.internetcloud.twodatasources.domain.model.DataSourceType
import ru.internetcloud.twodatasources.domain.usecase.WriteDataSourceTypeUseCase

class SettingsViewModel @Inject constructor(
    private val currentDataSourceType: CurrentDataSourceType,
    private val writeDataSourceTypeUseCase: WriteDataSourceTypeUseCase
) : ViewModel() {

    private var _dataSourceType = MutableLiveData<DataSourceType>()
    val dataSourceType: LiveData<DataSourceType>
    get() = _dataSourceType

    init {
        loadCurrentDataSource()
    }

    private fun loadCurrentDataSource() {
        currentDataSourceType.getCurrent()?.let { sourceType ->
            _dataSourceType.value = sourceType
        } ?: throw IllegalStateException("CurrentDataSourceType is not initialized in SettingsViewModel")
    }

    fun saveDataSourceTypeToStorage(dataSourceType: DataSourceType) {
        currentDataSourceType.setCurrent(dataSourceType)
        _dataSourceType.value = dataSourceType
        viewModelScope.launch(Dispatchers.IO) {
            writeDataSourceTypeUseCase.writeDataSourceType(dataSourceType)
        }
    }
}
