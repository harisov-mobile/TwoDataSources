package ru.internetcloud.twodatasources.di

import dagger.Module
import dagger.Provides
import ru.internetcloud.twodatasources.domain.model.CurrentDataSourceType

@Module
class DomainModule {

    @Provides
    fun provideCurrentDataSourceType(): CurrentDataSourceType {

        return CurrentDataSourceType
    }
}
