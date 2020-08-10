package com.droidproject.notes.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.core.text.isDigitsOnly
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.droidproject.notes.R
import com.droidproject.notes.db.Note
import com.droidproject.notes.db.NoteDatabase
import com.droidproject.notes.toast
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.android.synthetic.main.note_sample.*
import kotlinx.coroutines.launch


class AddNoteFragment : BaseFragment() {

    private var note: Note? = null
    private var noteId: Int? = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        noteId = note?.id
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    @SuppressLint("RestrictedApi")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            note = AddNoteFragmentArgs.fromBundle(it).note
            //don't use toString() inside otherwise it will print null inside Edittext
            faTitle.setText(note?.title)
            faNote.setText(note?.note)
        }

        btnDone.setOnClickListener(View.OnClickListener { view ->
            val title = faTitle.text.toString().trim()
            val strNote = faNote.text.toString().trim()

            if (title.isEmpty()) {
                faTitle.error = "title required"
                faTitle.requestFocus()
                return@OnClickListener
            }
            if (strNote.isEmpty()) {
                faNote.error = "note required"
                faNote.requestFocus()
                return@OnClickListener
            }

            launch {

                context?.let {
                    val mNote = Note(title, strNote)

                    if (note == null) {
                        NoteDatabase(it).getNoteDao().addNote(mNote)
                        it.toast("Note Saved")
                    } else {
                        mNote.id = note!!.id
                        NoteDatabase(it).getNoteDao().updateNote(mNote)
                        it.toast("Note Updated")
                    }

                    val action = AddNoteFragmentDirections.actionToHome()
                    Navigation.findNavController(view).navigate(action)
                    clearFindViewByIdCache()

                }
            }


        })

        if (note!= null){
            btnDelete.visibility = View.VISIBLE
        }else{
            btnDelete.visibility = View.GONE
        }

        btnDelete.setOnClickListener(View.OnClickListener {
            deleteNote()
        })

    }



    private fun deleteNote() {
        AlertDialog.Builder(context).apply {
            setTitle("Are you sure?")
            setPositiveButton("yes") { _, _ ->
                launch {
                    NoteDatabase(context).getNoteDao().deleteNote(note!!)
                    val action = AddNoteFragmentDirections.actionToHome()
                    Navigation.findNavController(this@AddNoteFragment.requireView())
                        .navigate(action)
                    clearFindViewByIdCache()
                }
            }
            setNegativeButton("No") { _, _ ->

            }
        }.create().show()
    }

}