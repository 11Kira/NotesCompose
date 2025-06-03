package com.kira.android.notescompose.features.notes

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private val useCase: NoteUseCase
) : ViewModel() {

    private val mutableNoteState: MutableSharedFlow<NoteState> = MutableSharedFlow()
    val noteState = mutableNoteState.asSharedFlow()

    fun getAllNotes(accountId: Long) {
        viewModelScope.launch(CoroutineExceptionHandler { _, error ->
            runBlocking {
                mutableNoteState.emit(NoteState.ShowError(error))
            }
        }) {
            val result = useCase.getAllNotes()
            mutableNoteState.emit(NoteState.SetNotesList(result))
        }
    }
}