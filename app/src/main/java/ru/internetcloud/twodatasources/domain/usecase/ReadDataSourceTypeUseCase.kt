package ru.internetcloud.twodatasources.domain.usecase

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import ru.internetcloud.twodatasources.domain.model.DataSourceType
import ru.internetcloud.twodatasources.domain.repository.DataStoreRepository

class ReadDataSourceTypeUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    fun readDataSourceType(): Flow<DataSourceType> {
        return dataStoreRepository.readDataSourceType()
    }
}
