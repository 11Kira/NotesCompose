package com.kira.android.notescompose.features.notes

import com.google.gson.JsonObject
import javax.inject.Inject

class NoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    suspend fun getAllNotes(): List<NoteResult> {
        return noteRepository.getAllNotes()
    }

    suspend fun getNoteById(noteId: String): NoteResult {
        return noteRepository.getNoteById(noteId = noteId)
    }

    suspend fun saveNote(body: JsonObject): NoteResult {
        return noteRepository.saveNote(body)
    }

    suspend fun updateNote(noteId: String, body: JsonObject): NoteResult {
        return noteRepository.updateNote(noteId = noteId, body = body)
    }

    suspend fun deleteNote(noteId: String): NoteResult {
        return noteRepository.deleteNote(noteId)
    }
}