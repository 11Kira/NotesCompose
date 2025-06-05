package com.kira.android.notescompose.features.notes.list

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.SharedFlow

lateinit var viewModel: NoteListViewModel

@Preview
@Composable
fun NoteListScreen() {
    viewModel = hiltViewModel()
    MainScreen(viewModel.noteListState)
    viewModel.getAllNotes()
}

@Composable
fun MainScreen(sharedFlow: SharedFlow<NoteListState>) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(key1 = Unit) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            sharedFlow.collect { state ->
                when (state) {
                    is NoteListState.SetNotesList -> {
                        state.notes.forEach {
                            Log.e("NOTES:", it.title)
                        }
                    }

                    is NoteListState.ShowError -> {

                    }
                }
            }
        }

    }
}