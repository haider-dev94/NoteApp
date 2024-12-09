package com.haiderdev94.noteapp.view

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.haiderdev94.noteapp.R
import com.haiderdev94.noteapp.databinding.AddEditNoteLayoutBinding
import com.haiderdev94.noteapp.model.Note


class AddEditNoteFragment : DialogFragment() {
    private var _binding: AddEditNoteLayoutBinding? = null
    private val binding get() = _binding!!

    private var listener: AddEditNoteListener? = null
    private var oldNote: Note? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.FullScreenDialog)
        listener = context as AddEditNoteListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddEditNoteLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        oldNote= if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
            arguments?.getSerializable("note",Note::class.java)
        }else{
            arguments?.getSerializable("note") as Note?
        }
        oldNote?.let {
            binding.edTitle.editText?.setText(it.title)
            binding.edContent.editText?.setText(it.content)
        }
        binding.btnClose.setOnClickListener {
            dismiss()
        }
        binding.btnSave.setOnClickListener {
            val title = binding.edTitle.editText?.text.toString().trim()
            val content = binding.edContent.editText?.text.toString().trim()
            val date = System.currentTimeMillis()
            if (title.isEmpty()) {
                Toast.makeText(context, "Plese Enter Title", Toast.LENGTH_LONG).show()
            } else if (content.isEmpty()) {
                Toast.makeText(context, "Plese Enter Content", Toast.LENGTH_LONG).show()
            } else {
                val note = Note(oldNote?.id, title = title, content = content, date = date)
                listener?.onSaveButtonClicked(note, oldNote != null)
                dismiss()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        if (dialog != null) {
            dialog.window?.statusBarColor =
                ContextCompat.getColor(requireContext(), R.color.dark_gray)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    interface AddEditNoteListener {
        fun onSaveButtonClicked(note: Note, isUpdate: Boolean)
    }


    companion object {
        const val TAG = "AddEditFragment"

        @JvmStatic
        fun newInstance(note: Note?) = AddEditNoteFragment().apply {
            arguments = Bundle().apply {
                putSerializable("note", note)
            }
        }
    }
}