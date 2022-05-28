package ru.internetcloud.twodatasources.domain.model

class CurrentDataSourceType {

    companion object {
        private var dataSourceType: DataSourceType? = null

        fun setCurrent(sourceType: DataSourceType) {
            dataSourceType = sourceType
        }

        fun getCurrent(): DataSourceType? {
            return dataSourceType
        }
    }
}
