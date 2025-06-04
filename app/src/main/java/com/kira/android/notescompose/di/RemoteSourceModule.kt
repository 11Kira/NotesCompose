package com.kira.android.notescompose.di

import com.kira.android.notescompose.features.notes.NoteRemoteSource
import com.kira.android.notescompose.features.notes.NoteService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteSourceModule {
    @Provides
    @Singleton
    fun provideNoteRemoteSource(
        noteService: NoteService
    ) = NoteRemoteSource(noteService = noteService)
}