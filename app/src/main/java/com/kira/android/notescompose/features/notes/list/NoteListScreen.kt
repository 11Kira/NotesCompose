package com.kira.android.notescompose.features.notes.list

import android.graphics.Typeface
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.kira.android.notescompose.features.notes.NoteResult
import androidx.core.graphics.toColorInt

lateinit var viewModel: NoteListViewModel

@Composable
fun NoteListScreen(onItemClicked: (String) -> Unit) {
    viewModel = hiltViewModel()
    MainScreen(onItemClicked)
    viewModel.getAllNotes()
}

@Composable
fun MainScreen(onItemClicked: (String) -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val notesList = remember { mutableStateListOf<NoteResult>() }

    LaunchedEffect(key1 = Unit) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.noteListState.collect { state ->
                when (state) {
                    is NoteListState.SetNotesList -> {
                        notesList.addAll(state.notes)
                    }

                    is NoteListState.ShowError -> {

                    }
                }
            }
        }
    }

    PopulateNoteListScreen(list = notesList, onItemClicked)
}

@Composable
fun PopulateNoteListScreen(list: List<NoteResult>, onItemClicked: (String) -> Unit) {
    Column {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Notes",
            style = TextStyle(
                color = Color("#FFA500".toColorInt()),
                fontFamily = FontFamily(typeface = Typeface.DEFAULT_BOLD),
                fontSize = 25.sp
            )
        )
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(list) { note ->
                NoteItem(
                    note,
                    { noteid -> onItemClicked.invoke(noteid) },
                    { },
                    { }
                )
            }
        }
    }
}

@Composable
fun NoteItem(
    note: NoteResult,
    onItemClicked: (String) -> Unit,
    onEditClick: (String) -> Unit,
    onDeleteClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .border(
                width = 1.dp,
                color = Color("#FFA500".toColorInt()),
                shape = RoundedCornerShape(11.dp)
            )
            .padding(16.dp)
            .clickable { onItemClicked.invoke(note.id) },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
        ) {
            Text(
                text = note.title,
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color("#FFA500".toColorInt()),
                    fontFamily = FontFamily(typeface = Typeface.DEFAULT_BOLD)
                )
            )

            Row {
                IconButton(
                    onClick = {
                        onEditClick.invoke(note.id)
                    },
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = Color("#FFA500".toColorInt())
                    )
                }

                IconButton(
                    onClick = {
                        onDeleteClick.invoke(note.id)
                    },
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color("#FF0000".toColorInt())
                    )
                }
            }
        }

        Text(
            modifier = Modifier.padding(top = 10.dp),
            style = TextStyle(
                fontSize = 12.sp,
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            text = note.body
        )
    }
}