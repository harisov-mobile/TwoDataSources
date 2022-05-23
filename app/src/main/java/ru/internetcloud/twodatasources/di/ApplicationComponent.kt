package ru.internetcloud.twodatasources.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.internetcloud.twodatasources.presentation.edit_note.EditNoteFragment
import ru.internetcloud.twodatasources.presentation.note_list.NoteListFragment

@Component(modules = [DomainModule::class, DataModule::class, ViewModelModule::class])
@ApplicationScope
interface ApplicationComponent {

    fun inject(fragment: NoteListFragment)

    fun inject(fragment: EditNoteFragment)

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance application: Application): ApplicationComponent
    }
}