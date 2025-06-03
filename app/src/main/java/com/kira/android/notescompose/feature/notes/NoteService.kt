package com.kira.android.notescompose.feature.notes

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface NoteService {
    @GET("/notes")
    suspend fun getAllNotes(): Response<List<NoteResult>>

    @GET("/notes/{id}")
    suspend fun getNoteById(
        @Path("id") id: String,
    ): Response<NoteResult>

    @POST("/notes")
    suspend fun saveNote(
        @Body body: JsonObject
    ): Response<NoteResult>

    @PUT("/notes/{id}")
    suspend fun updateNote(
        @Body body: JsonObject
    ): Response<NoteResult>

    @DELETE("/notes/{id}")
    suspend fun deleteNote(
        @Path("id") id: String
    ): Response<NoteResult>
}