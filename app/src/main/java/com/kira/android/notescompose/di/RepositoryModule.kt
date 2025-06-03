package com.kira.android.notescompose.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideNoteRepository(
        noteRemoteSource: NoteRemoteSource
    ) = provideNoteRepository(remoteSource = noteRemoteSource)
}