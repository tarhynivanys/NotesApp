package com.kozin.notesnotes.repository

import androidx.lifecycle.LiveData
import com.kozin.notesnotes.data.NotesDao
import com.kozin.notesnotes.model.Notes

class NotesRepository(private val notesDao: NotesDao) {

    val readAllData: LiveData<List<Notes>> = notesDao.readAllData()

    suspend fun addNotes(notes: Notes){
        notesDao.addNotes(notes)
    }

    suspend fun updateNotes(notes: Notes){
        notesDao.updateNotes(notes)
    }

    suspend fun deleteNotes(notes: Notes){
        notesDao.deleteNotes(notes)
    }

    suspend fun deleteAllNotes(){
        notesDao.deleteAllNotes()
    }

}