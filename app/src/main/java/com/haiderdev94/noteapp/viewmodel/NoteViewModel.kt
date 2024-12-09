package com.haiderdev94.noteapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haiderdev94.noteapp.model.Note
import com.haiderdev94.noteapp.repository.NoteRepositoryImpl
import kotlinx.coroutines.launch

class NoteViewModel(private val noteRepositoryImpl: NoteRepositoryImpl) : ViewModel() {
    val getAllNotes: LiveData<List<Note>> = noteRepositoryImpl.getAllNote()

    fun saveNote(note: Note) = viewModelScope.launch {
        noteRepositoryImpl.saveNote(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        noteRepositoryImpl.updateNote(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        noteRepositoryImpl.deleteNote(note)
    }

    fun searchNote(query:String?) = noteRepositoryImpl.searchNote(query)
}