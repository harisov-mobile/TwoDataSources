package ru.internetcloud.twodatasources.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.internetcloud.twodatasources.domain.model.DataSourceType

interface DataStoreRepository {

    fun readDataSourceType(): Flow<DataSourceType>

    suspend fun writeDataSourceType(dataSourceType: DataSourceType)
}
