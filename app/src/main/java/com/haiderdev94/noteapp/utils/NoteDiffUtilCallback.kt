package com.haiderdev94.noteapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.haiderdev94.noteapp.model.Note

object NoteDiffUtilCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id==newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }
}