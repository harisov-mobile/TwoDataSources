package ru.internetcloud.twodatasources.domain.usecase

import javax.inject.Inject
import ru.internetcloud.twodatasources.domain.model.DataSourceType
import ru.internetcloud.twodatasources.domain.repository.DataStoreRepository

class WriteDataSourceTypeUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend fun writeDataSourceType(dataSourceType: DataSourceType) {
        dataStoreRepository.writeDataSourceType(dataSourceType)
    }
}
