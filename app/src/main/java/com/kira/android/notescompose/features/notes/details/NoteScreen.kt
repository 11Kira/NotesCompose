package com.kira.android.notescompose.features.notes.details

import android.graphics.Typeface
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.kira.android.notescompose.features.notes.NoteResult
import kotlinx.coroutines.flow.SharedFlow

lateinit var viewModel: NoteViewModel

@Composable
fun NoteScreen(noteId: String?) {
    viewModel = hiltViewModel()
    MainScreen(viewModel.noteState)
    noteId?.let { viewModel.getNoteById(it) }
}

@Composable
fun MainScreen(sharedFlow: SharedFlow<NoteState>) {
    val lifecycleOwner = LocalLifecycleOwner.current
    var selectedNote by remember { mutableStateOf<NoteResult?>(null) }

    LaunchedEffect(key1 = Unit) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            sharedFlow.collect { state ->
                when (state) {
                    is NoteState.SetNote -> {
                        selectedNote = state.note
                    }

                    is NoteState.ShowError -> {}
                }
            }
        }
    }

    PopulateNote()
}

@Composable
fun PopulateNote() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "Create a Note",
            style = TextStyle(
                color = Color("#FFA500".toColorInt()),
                fontFamily = FontFamily(typeface = Typeface.DEFAULT_BOLD),
                fontSize = 25.sp
            )
        )
    }
}