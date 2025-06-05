package com.kira.android.notescompose.features.notes.details

import com.kira.android.notescompose.features.notes.NoteResult

sealed class NoteState {
    data class ShowError(val error: Any) : NoteState()
    data class SetNote(val notes: NoteResult) : NoteState()
}