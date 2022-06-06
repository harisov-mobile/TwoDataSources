package ru.internetcloud.twodatasources.presentation.settings

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters
import org.mockito.Mockito
import org.mockito.kotlin.mock
import ru.internetcloud.twodatasources.domain.model.CurrentDataSourceType
import ru.internetcloud.twodatasources.domain.model.DataSourceType
import ru.internetcloud.twodatasources.domain.repository.DataStoreRepository
import ru.internetcloud.twodatasources.domain.usecase.WriteDataSourceTypeUseCase

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class SettingsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    val dataStoreRepository = mock<DataStoreRepository>()
    val writeDataSourceTypeUseCase = mock<WriteDataSourceTypeUseCase>()

    @Before
    fun beforeEach() {
    }

    @After
    fun afterEach() {
        Mockito.reset(dataStoreRepository)
        Mockito.reset(writeDataSourceTypeUseCase)
    }

    @Test(expected = IllegalStateException::class)
    fun a_load_datasource_type_throw_exception() {

        val viewModel = SettingsViewModel(CurrentDataSourceType, writeDataSourceTypeUseCase)
        // метод должын быть вызван первым, т.к. CurrentDataSourceType еще не проинициализирован,
        // и при методе init  будет выброшено исключение
    }

    @Test
    fun b_loadCurrentDataSource_sets_dataSourceType() {

        val expected = DataSourceType.ROOM_DATABASE1

        CurrentDataSourceType.setCurrent(expected)

        // при создании SettingsViewModel будет инициализировано свойство:
        // val dataSourceType: LiveData<DataSourceType>
        val viewModel = SettingsViewModel(CurrentDataSourceType, writeDataSourceTypeUseCase)

        val actual = viewModel.dataSourceType.value

        assertEquals(expected, actual)
    }

    @Test
    fun c_saveDataSourceTypeToStorage_sets_CurrentDataSourceType_with_ROOM_DATABASE1() {

        // whenever(writeDataSourceTypeUseCase.writeDataSourceType(any())).thenReturn()

        CurrentDataSourceType.setCurrent(DataSourceType.ROOM_DATABASE2)
        val viewModel = SettingsViewModel(CurrentDataSourceType, writeDataSourceTypeUseCase)

        val expected = DataSourceType.ROOM_DATABASE1

        viewModel.saveDataSourceTypeToStorage(expected)

        val actual = CurrentDataSourceType.getCurrent()

        assertEquals(expected, actual)
    }

    @Test
    fun d_saveDataSourceTypeToStorage_sets_CurrentDataSourceType_with_ROOM_DATABASE2() {

        // whenever(writeDataSourceTypeUseCase.writeDataSourceType(any())).thenReturn()

        CurrentDataSourceType.setCurrent(DataSourceType.ROOM_DATABASE1)
        val viewModel = SettingsViewModel(CurrentDataSourceType, writeDataSourceTypeUseCase)

        val expected = DataSourceType.ROOM_DATABASE2

        viewModel.saveDataSourceTypeToStorage(expected)

        val actual = CurrentDataSourceType.getCurrent()

        assertEquals(expected, actual)
    }

    @Test
    fun e_saveDataSourceTypeToStorage_changes_dataSourceType() {

        val observer: Observer<DataSourceType> = mock()

        CurrentDataSourceType.setCurrent(DataSourceType.ROOM_DATABASE1)
        val viewModel = SettingsViewModel(CurrentDataSourceType, writeDataSourceTypeUseCase)

        val expected = DataSourceType.ROOM_DATABASE2

        viewModel.saveDataSourceTypeToStorage(expected)

        val actual = CurrentDataSourceType.getCurrent()

        assertEquals(expected, actual)
    }
}
