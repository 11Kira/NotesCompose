package com.kira.android.notescompose.feature.notes

import com.google.gson.JsonObject
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteRemoteSource: NoteRemoteSource
) {
    suspend fun getAllNotes(): List<NoteResult> {
        return noteRemoteSource.getAllNotes()
    }

    suspend fun getNoteById(id: String): NoteResult {
        return noteRemoteSource.getNoteById(id = id)
    }

    suspend fun saveNote(body: JsonObject): NoteResult {
        return noteRemoteSource.updateNote(body)
    }

    suspend fun updateNote(body: JsonObject): NoteResult {
        return noteRemoteSource.updateNote(body = body)
    }

    suspend fun deleteNote(id: String): NoteResult {
        return noteRemoteSource.deleteNote(id)
    }
}