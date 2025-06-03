package com.kira.android.notescompose.features.notes

sealed class NoteState {
    data class ShowError(val error: Any) : NoteState()
    data class SetNotesList(val notes: List<NoteResult>) : NoteState()
}