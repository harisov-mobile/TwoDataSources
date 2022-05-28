package ru.internetcloud.twodatasources.di

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.internetcloud.twodatasources.data.database.AppDao
import ru.internetcloud.twodatasources.data.database.AppDao2
import ru.internetcloud.twodatasources.data.database.AppDatabase
import ru.internetcloud.twodatasources.data.database.AppDatabase2
import ru.internetcloud.twodatasources.data.repository.NoteRepositoryImpl
import ru.internetcloud.twodatasources.domain.repository.NoteRepository

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindNoteRepository(impl: NoteRepositoryImpl): NoteRepository

    companion object {

        @ApplicationScope
        @Provides
        fun provideAppDao(
            application: Application
        ): AppDao {
            return AppDatabase.getInstance(application).appDao()
        }

        @ApplicationScope
        @Provides
        fun provideAppDao2(
            application: Application
        ): AppDao2 {
            return AppDatabase2.getInstance(application).appDao2()
        }
    }
}
