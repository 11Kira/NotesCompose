package com.kira.android.notescompose.features.notes.details

import android.graphics.Typeface
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import com.kira.android.notescompose.features.notes.NoteResult
import kotlinx.coroutines.flow.SharedFlow

lateinit var viewModel: NoteViewModel

@Composable
fun NoteScreen(noteId: String?, navController: NavHostController) {
    viewModel = hiltViewModel()
    MainScreen(viewModel.noteState, navController)
    noteId?.let {
        if (it.isNotEmpty()) viewModel.getNoteById(it)
    }
}

@Composable
fun MainScreen(sharedFlow: SharedFlow<NoteState>, navController: NavHostController) {
    val lifecycleOwner = LocalLifecycleOwner.current
    var selectedNote by remember { mutableStateOf<NoteResult?>(null) }

    LaunchedEffect(key1 = Unit) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            sharedFlow.collect { state ->
                when (state) {
                    is NoteState.SetNote -> {
                        selectedNote = state.note
                    }

                    is NoteState.ShowError -> {
                        Log.e("test", state.error.toString())
                    }
                }
            }
        }
    }

    PopulateNote(selectedNote, navController)
}

@Composable
fun PopulateNote(selectedNote: NoteResult?, navController: NavHostController) {
    var selectedNoteTitle by remember { mutableStateOf("") }
    var selectedNoteBody by remember { mutableStateOf("") }
    var titleError by remember { mutableStateOf(false) }
    var bodyError by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(key1 = selectedNote) {
        selectedNoteTitle = selectedNote?.title ?: ""
        selectedNoteBody = selectedNote?.body ?: ""
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            Text(
                modifier = Modifier.align(Alignment.CenterStart),
                text = if (selectedNote != null) "Edit Note" else "Add Note",
                style = TextStyle(
                    color = Color("#FFA500".toColorInt()),
                    fontFamily = FontFamily(typeface = Typeface.DEFAULT_BOLD),
                    fontSize = 25.sp
                )
            )

            IconButton(
                onClick = {
                    titleError = selectedNoteTitle.isBlank()
                    bodyError = selectedNoteBody.isBlank()
                    if (!titleError && !bodyError) {
                        if (selectedNote != null) {
                            viewModel.updateNote(
                                selectedNote.id,
                                selectedNoteTitle,
                                selectedNoteBody
                            )
                            Toast.makeText(context, "Note updated!", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        } else {
                            viewModel.saveNewNote(selectedNoteTitle, selectedNoteBody)
                            Toast.makeText(context, "Note saved!", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        }
                    }
                },
                modifier = Modifier
                    .width(35.dp)
                    .height(35.dp)
                    .align(Alignment.CenterEnd),
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Save",
                    tint = Color("#FFA500".toColorInt())
                )
            }
        }

        OutlinedTextField(
            value = selectedNoteTitle,
            onValueChange = { newTitle ->
                selectedNoteTitle = newTitle
                titleError = newTitle.isBlank()
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            shape = RoundedCornerShape(11.dp),
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = Color.Black,
                focusedBorderColor = Color("#FFA500".toColorInt()),
                unfocusedBorderColor = Color("#FFA500".toColorInt()),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
            ),
            isError = titleError,
            label = {
                Text(
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = Color("#FFA500".toColorInt()),
                        fontFamily = FontFamily(typeface = Typeface.DEFAULT_BOLD)
                    ),
                    text = "Title"
                )
            },
            supportingText = {
                if (titleError) {
                    Text(
                        text = "Title cannot be empty",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

        )

        OutlinedTextField(
            value = selectedNoteBody,
            onValueChange = { newBody ->
                selectedNoteBody = newBody
                bodyError = newBody.isBlank()
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            shape = RoundedCornerShape(11.dp),
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = Color.Black,
                focusedBorderColor = Color("#FFA500".toColorInt()),
                unfocusedBorderColor = Color("#FFA500".toColorInt()),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
            ),
            isError = bodyError,
            label = {
                Text(
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = Color("#FFA500".toColorInt()),
                        fontFamily = FontFamily(typeface = Typeface.DEFAULT_BOLD)
                    ),
                    text = "Description"
                )
            },
            supportingText = {
                if (bodyError) {
                    Text(
                        text = "Body cannot be empty",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        )
    }
}