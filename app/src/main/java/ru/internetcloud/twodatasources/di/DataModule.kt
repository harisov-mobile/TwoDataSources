package ru.internetcloud.twodatasources.di

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.internetcloud.twodatasources.data.database.AppDao
import ru.internetcloud.twodatasources.data.database.AppDatabase
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
    }
}
