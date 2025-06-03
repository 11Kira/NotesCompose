package com.kira.android.notescompose.di

import com.kira.android.notescompose.features.notes.NoteService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {
    @Singleton
    @Provides
    fun provideNoteService(retrofit: Retrofit): NoteService =
        retrofit.create(NoteService::class.java)
}