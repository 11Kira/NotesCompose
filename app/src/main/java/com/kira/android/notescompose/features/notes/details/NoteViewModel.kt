package com.kira.android.notescompose.features.notes.details

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.kira.android.notescompose.features.notes.NoteUseCase
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

    fun getNoteById(noteId: String) {
        viewModelScope.launch(CoroutineExceptionHandler { _, error ->
            runBlocking {
                mutableNoteState.emit(NoteState.ShowError(error))
            }
        }) {
            val result = useCase.getNoteById(noteId)
            mutableNoteState.emit(NoteState.SetNote(result))
        }
    }

    fun updateNote(noteId: String, title: String, body: String) {
        viewModelScope.launch(CoroutineExceptionHandler { _, error ->
            runBlocking {
                mutableNoteState.emit(NoteState.ShowError(error))
            }
        }) {
            val jsonObject = JsonObject()
            jsonObject.addProperty("title", title)
            jsonObject.addProperty("body", body)

            val result = useCase.updateNote(noteId, jsonObject)
            mutableNoteState.emit(NoteState.SetNote(result))
        }
    }
}