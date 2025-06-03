package com.kira.android.notescompose.features.notes

import com.google.gson.JsonObject
import javax.inject.Inject

class NoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    suspend fun getAllNotes(): List<NoteResult> {
        return noteRepository.getAllNotes()
    }

    suspend fun getNoteById(id: String): NoteResult {
        return noteRepository.getNoteById(id = id)
    }

    suspend fun saveNote(body: JsonObject): NoteResult {
        return noteRepository.updateNote(body)
    }

    suspend fun updateNote(body: JsonObject): NoteResult {
        return noteRepository.updateNote(body = body)
    }

    suspend fun deleteNote(id: String): NoteResult {
        return noteRepository.deleteNote(id)
    }
}