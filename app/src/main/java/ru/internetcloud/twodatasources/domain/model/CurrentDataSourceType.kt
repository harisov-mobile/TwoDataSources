package ru.internetcloud.twodatasources.domain.model

object CurrentDataSourceType {

    private var dataSourceType: DataSourceType? = null

    fun setCurrent(sourceType: DataSourceType) {
        dataSourceType = sourceType
    }

    fun getCurrent(): DataSourceType? {
        return dataSourceType
    }
}
