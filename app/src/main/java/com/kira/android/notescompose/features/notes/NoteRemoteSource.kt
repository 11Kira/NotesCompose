package com.kira.android.notescompose.features.notes

import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NoteRemoteSource @Inject constructor(
    private val noteService: NoteService
) {
    suspend fun getAllNotes() = withContext(Dispatchers.IO) { noteService.getAllNotes() }

    suspend fun getNoteById(noteId: String) =
        withContext(Dispatchers.IO) { noteService.getNoteById(id = noteId) }

    suspend fun deleteNote(noteId: String) {
        withContext(Dispatchers.IO) { noteService.deleteNote(id = noteId) }
    }

    suspend fun saveNote(body: JsonObject) =
        withContext(Dispatchers.IO) { noteService.saveNote(body = body) }

    suspend fun updateNote(noteId: String, body: JsonObject) =
        withContext(Dispatchers.IO) { noteService.updateNote(id = noteId, body = body) }
}