package com.droidproject.notes.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.droidproject.notes.R
import com.droidproject.notes.db.NoteDatabase
import com.droidproject.notes.toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.note_sample.*
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_home, container, false)

        return v
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        launch {
            context?.let {
                val notes = NoteDatabase(it).getNoteDao().getAllNotes()
                recyclerView.apply {
                    setHasFixedSize(true)
                    layoutManager =  StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                    recyclerView.adapter = NotesAdapter(notes)
                }

            }
        }


        fhFloatingActionButton.setOnClickListener(View.OnClickListener {

            Navigation.findNavController(it).navigate(R.id.actionAddNote)

        })

    }

}