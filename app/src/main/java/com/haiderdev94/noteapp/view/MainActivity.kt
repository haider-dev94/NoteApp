package com.haiderdev94.noteapp.view

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.haiderdev94.noteapp.R
import com.haiderdev94.noteapp.databinding.ActivityMainBinding
import com.haiderdev94.noteapp.db.AppDatabase
import com.haiderdev94.noteapp.model.Note
import com.haiderdev94.noteapp.repository.NoteRepositoryImpl
import com.haiderdev94.noteapp.utils.RecyclerViewClickListener
import com.haiderdev94.noteapp.viewmodel.NoteViewModel
import com.haiderdev94.noteapp.viewmodel.NoteViewModelFactory

class MainActivity : AppCompatActivity(), AddEditNoteFragment.AddEditNoteListener,
    RecyclerViewClickListener {
    private lateinit var binding: ActivityMainBinding
    private val TAG = "MainActivity"
    private lateinit var viewModel: NoteViewModel
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = NoteRepositoryImpl(AppDatabase.getDatabase(this).getNoteDao())
        val factory = NoteViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[NoteViewModel::class.java]
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.addNote.setOnClickListener {
            val dialog = AddEditNoteFragment.newInstance(null)
            dialog.show(supportFragmentManager, AddEditNoteFragment.TAG)
        }
        val searchIcon =
            binding.searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_button)
        searchIcon.setColorFilter(ContextCompat.getColor(this, R.color.green))
        val closeIcon =
            binding.searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
        closeIcon.setColorFilter(ContextCompat.getColor(this, R.color.green))
        val hintIcon =
            binding.searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_mag_icon)
        hintIcon.setColorFilter(ContextCompat.getColor(this, R.color.light_gray))
        val goIcon =
            binding.searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_go_btn)
        goIcon.setColorFilter(ContextCompat.getColor(this, R.color.light_gray))
        val searchText =
            binding.searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        searchText.setTypeface(null, Typeface.BOLD)
        searchText.setTextColor(ContextCompat.getColor(this, R.color.light_gray))
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        adapter = NoteAdapter(this)
        binding.recyclerView.adapter = adapter
        viewModel.getAllNotes.observe(this) {
            it?.let {
                adapter.mDiffer.submitList(it)
                binding.recyclerView.visibility = View.VISIBLE
                if(it.size != 0){
                    binding.imgNoData.visibility=View.GONE
                }else{
                    binding.imgNoData.visibility=View.VISIBLE
                }
            }
        }
        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    searchNotes(newText)
                }
                return true
            }
        })
    }

    private fun searchNotes(query: String) {
        viewModel.searchNote(query).observe(this) { notes ->
            adapter.mDiffer.submitList(notes)
        }
    }

    override fun onSaveButtonClicked(note: Note, isUpdate: Boolean) {
        if (isUpdate) {
            viewModel.updateNote(note)
        } else {
            viewModel.saveNote(note)
        }
    }

    override fun onItemClickListener(note: Note) {
        val dialog = AddEditNoteFragment.newInstance(note)
        dialog.show(supportFragmentManager, AddEditNoteFragment.TAG)
    }

    override fun onDeleteClickListener(note: Note) {
        viewModel.deleteNote(note)
    }
}