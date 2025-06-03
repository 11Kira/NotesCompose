package com.kira.android.notescompose.feature.notes

import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NoteRemoteSource @Inject constructor(
    private val noteService: NoteService
) {
    suspend fun getAllNotes() = withContext(Dispatchers.IO) { noteService.getAllNotes() }

    suspend fun getNoteById(id: String) =
        withContext(Dispatchers.IO) { noteService.getNoteById(id = id) }

    suspend fun deleteNote(id: String) =
        withContext(Dispatchers.IO) { noteService.deleteNote(id = id) }

    suspend fun saveNote(body: JsonObject) =
        withContext(Dispatchers.IO) { noteService.saveNote(body = body) }

    suspend fun updateNote(body: JsonObject) =
        withContext(Dispatchers.IO) { noteService.updateNote(body = body) }
}