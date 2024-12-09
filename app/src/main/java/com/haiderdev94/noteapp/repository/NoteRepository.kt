package com.haiderdev94.noteapp.repository

import androidx.lifecycle.LiveData
import com.haiderdev94.noteapp.model.Note

interface NoteRepository {
    fun getAllNote():LiveData<List<Note>>
    suspend fun saveNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(note: Note)
    fun searchNote(query:String?): LiveData<List<Note>>


}