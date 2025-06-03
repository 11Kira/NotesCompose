package com.kira.android.notescompose.di

import com.kira.android.notescompose.features.notes.NoteRepository
import com.kira.android.notescompose.features.notes.NoteUseCase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {
    @Provides
    @ViewModelScoped
    fun provideNoteUseCase(
        repository: NoteRepository
    ) = NoteUseCase(repository)
}