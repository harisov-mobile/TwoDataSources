package ru.internetcloud.twodatasources.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.internetcloud.twodatasources.presentation.edit_note.EditNoteFragment
import ru.internetcloud.twodatasources.presentation.note_list.NoteListFragment
import ru.internetcloud.twodatasources.presentation.settings.SettingsFragment

@Component(modules = [DomainModule::class, DataModule::class, ViewModelModule::class])
@ApplicationScope
interface ApplicationComponent {

    fun inject(fragment: NoteListFragment)

    fun inject(fragment: EditNoteFragment)

    fun inject(fragment: SettingsFragment)

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance application: Application): ApplicationComponent
    }
}
