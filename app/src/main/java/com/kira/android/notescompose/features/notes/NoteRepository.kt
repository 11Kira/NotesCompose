package com.kira.android.notescompose.features.notes

import com.google.gson.JsonObject
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteRemoteSource: NoteRemoteSource
) {
    suspend fun getAllNotes(): List<NoteResult> {
        return noteRemoteSource.getAllNotes()
    }

    suspend fun getNoteById(noteId: String): NoteResult {
        return noteRemoteSource.getNoteById(noteId = noteId)
    }

    suspend fun saveNote(body: JsonObject): NoteResult {
        return noteRemoteSource.saveNote(body)
    }

    suspend fun updateNote(noteId: String, body: JsonObject): NoteResult {
        return noteRemoteSource.updateNote(noteId = noteId, body = body)
    }

    suspend fun deleteNote(noteId: String): NoteResult {
        return noteRemoteSource.deleteNote(noteId = noteId)
    }
}