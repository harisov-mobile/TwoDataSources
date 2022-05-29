package ru.internetcloud.twodatasources.data.repository

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import ru.internetcloud.twodatasources.domain.model.DataSourceType
import ru.internetcloud.twodatasources.domain.repository.DataStoreRepository

class DataStoreRepositoryImpl @Inject constructor(
    private val application: Application
) : DataStoreRepository {

    companion object {
        private const val DATA_SOURCE_TYPE = "data_source_type"
        private const val DATA_SOURCE_DEFAULT_VALUE = 0
        private const val PREFERENCES_NAME = "app_notes_preferences"
    }

    private object PreferenceKeys {
        val selectedDataSourceType = intPreferencesKey(DATA_SOURCE_TYPE)
    }

    private val Context.appNotesDataStore by preferencesDataStore(name = PREFERENCES_NAME)

    private val flowDataSourceType: Flow<DataSourceType> = application.appNotesDataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }
        .map { settings ->
            val intDataStoreType: Int = settings[PreferenceKeys.selectedDataSourceType] ?: DATA_SOURCE_DEFAULT_VALUE

            when (intDataStoreType) {
                0 -> DataSourceType.ROOM_DATABASE1
                1 -> DataSourceType.ROOM_DATABASE2
                else -> DataSourceType.ROOM_DATABASE1
            }
        }

    override fun readDataSourceType(): Flow<DataSourceType> {
        return flowDataSourceType
    }

    override suspend fun writeDataSourceType(dataSourceType: DataSourceType) {
        application.appNotesDataStore.edit { settings ->
            settings[PreferenceKeys.selectedDataSourceType] = when (dataSourceType) {
                DataSourceType.ROOM_DATABASE1 -> 0
                DataSourceType.ROOM_DATABASE2 -> 1
            }
        }
    }
}
