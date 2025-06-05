package com.kira.android.notescompose.features.notes.list

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.kira.android.notescompose.features.notes.NoteResult
import kotlinx.coroutines.flow.SharedFlow
import androidx.core.graphics.toColorInt

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
    var notesList = remember { mutableStateListOf<NoteResult>() }

    LaunchedEffect(key1 = Unit) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            sharedFlow.collect { state ->
                when (state) {
                    is NoteListState.SetNotesList -> {
                        /*notesList = state.notes.toMutableStateList()
                        Log.e("test count", state.notes.size.toString())*/
                        state.notes.forEach {
                            notesList.add(it)
                        }
                    }

                    is NoteListState.ShowError -> {

                    }
                }
            }
        }
    }

    PopulateNoteListScreen(list = notesList)
}

@Composable
fun PopulateNoteListScreen(list: List<NoteResult>) {
    Column {
        Text(
            modifier = Modifier
                .padding(16.dp),
            text = "Notes",
            style = TextStyle(
                color = Color("#FFA500".toColorInt()),
                fontSize = 20.sp
            )
        )
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(list) { note ->
                NoteItem(note)
            }
        }
    }
}

@Composable
fun NoteItem(note: NoteResult) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .border(
                width = 1.dp,
                color = Color("#FFA500".toColorInt()),
                shape = RoundedCornerShape(11.dp)
            )
            .padding(16.dp)
    ) {
        Text(
            text = note.title,
            style = TextStyle(
                color = Color("#FFA500".toColorInt())
            )
        )
        Text(
            modifier = Modifier.padding(top = 12.dp),
            text = note.body
        )
    }
}