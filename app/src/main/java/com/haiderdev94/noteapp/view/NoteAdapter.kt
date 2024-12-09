package com.haiderdev94.noteapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.haiderdev94.noteapp.databinding.NoteItemLayoutBinding
import com.haiderdev94.noteapp.utils.DateConverter
import com.haiderdev94.noteapp.utils.NoteDiffUtilCallback
import com.haiderdev94.noteapp.utils.RecyclerViewClickListener

class NoteAdapter(
    private val listener: RecyclerViewClickListener
) : RecyclerView.Adapter<NoteAdapter.MyViewHolder>() {

   val mDiffer=AsyncListDiffer(this,NoteDiffUtilCallback)

   inner class MyViewHolder(val binding: NoteItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            NoteItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val note = mDiffer.currentList[position]
        holder.binding.tvTitle.text = note.title
        holder.binding.tvContent.text = note.content
        holder.binding.tvDate.text= DateConverter.fromTimestampToString(note.date)
        holder.binding.root.setOnClickListener {
            listener.onItemClickListener(note)
        }
        holder.binding.btnDelete.setOnClickListener {
            listener.onDeleteClickListener(note)
        }
    }

    override fun getItemCount(): Int = mDiffer.currentList.size

}