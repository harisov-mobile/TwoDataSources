package ru.internetcloud.twodatasources.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.internetcloud.twodatasources.presentation.edit_note.EditNoteViewModel
import ru.internetcloud.twodatasources.presentation.note_list.NoteListViewModel
import ru.internetcloud.twodatasources.presentation.settings.SettingsViewModel

@Module
interface ViewModelModule {

    // перечислить тут все вью-модели
    @IntoMap
    @ViewModelKey(NoteListViewModel::class)
    @Binds
    fun bindNoteListViewModel(impl: NoteListViewModel): ViewModel

    @IntoMap
    @ViewModelKey(EditNoteViewModel::class)
    @Binds
    fun bindEditNoteViewModel(impl: EditNoteViewModel): ViewModel

    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    @Binds
    fun bindSettingsViewModel(impl: SettingsViewModel): ViewModel
}
