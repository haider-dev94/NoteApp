package com.haiderdev94.noteapp.repository

import androidx.lifecycle.LiveData
import com.haiderdev94.noteapp.db.NoteDao
import com.haiderdev94.noteapp.model.Note

class NoteRepositoryImpl(private val noteDao: NoteDao):NoteRepository {

    override fun getAllNote(): LiveData<List<Note>> {
        return noteDao.getAllNotes()
    }

    override suspend fun saveNote(note: Note) {
        noteDao.saveNote(note)
    }

    override suspend fun updateNote(note: Note) {
        noteDao.updateNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }

    override fun searchNote(query: String?): LiveData<List<Note>> {
       return noteDao.searchNote(query)
    }
}