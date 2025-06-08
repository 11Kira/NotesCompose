package com.kira.android.notescompose.features.notes.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kira.android.notescompose.features.notes.NoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val useCase: NoteUseCase
): ViewModel() {
    private val mutableNoteListState: MutableSharedFlow<NoteListState> = MutableSharedFlow()
    val noteListState = mutableNoteListState.asSharedFlow()

    fun getAllNotes() {
        viewModelScope.launch(CoroutineExceptionHandler { _, error ->
            runBlocking {
                mutableNoteListState.emit(NoteListState.ShowError(error))
            }
        }) {
            val result = useCase.getAllNotes()
            mutableNoteListState.emit(NoteListState.SetNotesList(result))
        }
    }

    fun deleteNoteById(noteId: String) {
        viewModelScope.launch(CoroutineExceptionHandler { _, error ->
            runBlocking {
                mutableNoteListState.emit(NoteListState.ShowError(error))
            }
        }) {
            useCase.deleteNote(noteId)
            getAllNotes()
        }
    }
}