package com.kira.android.notescompose.features.notes.list

import com.kira.android.notescompose.features.notes.NoteResult

sealed class NoteListState {
    data class ShowError(val error: Any) : NoteListState()
    data class SetNotesList(val notes: List<NoteResult>) : NoteListState()
}