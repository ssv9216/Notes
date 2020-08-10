package com.droidproject.notes.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.droidproject.notes.R
import com.droidproject.notes.db.Note
import com.droidproject.notes.db.NoteDatabase
import com.droidproject.notes.toast
import kotlinx.android.synthetic.main.note_sample.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope

class NotesAdapter(private val list: List<Note>) :RecyclerView.Adapter<NotesAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.note_sample,parent,false)
        )
    }
    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mTitle.text = list[position].title
        holder.mNote.text = list[position].note

        holder.itemView.setOnClickListener(View.OnClickListener {
            val action = HomeFragmentDirections.actionAddNote(list[position])
            Navigation.findNavController(it).navigate(action)
        })
    }
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val mTitle :TextView= itemView.title
        val mNote: TextView = itemView.note
    }

}
