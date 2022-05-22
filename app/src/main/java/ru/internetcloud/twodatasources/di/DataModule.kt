package ru.internetcloud.twodatasources.di

import dagger.Module

@Module
interface DataModule {

    companion object {

//        @ApplicationScope
//        @Provides
//        fun provideAppDao(
//            application: Application
//        ): AppDao {
//            return AppDatabase.getInstance(application).appDao()
//        }
    }
}