package com.haiderdev94.noteapp.utils

import com.haiderdev94.noteapp.model.Note

interface RecyclerViewClickListener {
    fun onItemClickListener(note: Note)
    fun onDeleteClickListener(note:Note)
}