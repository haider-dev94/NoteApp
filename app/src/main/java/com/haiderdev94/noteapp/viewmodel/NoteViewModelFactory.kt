package com.haiderdev94.noteapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.haiderdev94.noteapp.repository.NoteRepositoryImpl

@Suppress("UNCHECKED_CAST")
class NoteViewModelFactory(private val repositoryImpl: NoteRepositoryImpl):ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteViewModel(repositoryImpl) as T
    }
}