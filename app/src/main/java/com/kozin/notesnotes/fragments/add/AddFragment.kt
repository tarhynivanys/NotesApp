package com.kozin.notesnotes.fragments.add

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kozin.notesnotes.R
import com.kozin.notesnotes.model.Notes
import com.kozin.notesnotes.viewmodel.NotesViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*

class AddFragment : Fragment() {

    private lateinit var mNotesViewModel: NotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        mNotesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

        view.add_button.setOnClickListener {
            insertDataToDatabase()
        }

        return view
    }

    private fun insertDataToDatabase() {

        val header = header_et.text.toString()
        val desc = descr_et.text.toString()

        if (inputCheck(header)
            && header.length <= 30
            && desc.isNotEmpty()
            && desc.length <= 750){

            // Create Notes obj
            val notes = Notes(0, header, desc)

            // Add data to database
            mNotesViewModel.addNotes(notes)
            Toast.makeText(requireContext(), "Successfully inserted", Toast.LENGTH_SHORT).show()

            // Navigate back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)

        }else{
            Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()

        }

    }

    private fun inputCheck(header: String): Boolean {
        return !(TextUtils.isEmpty(header))
    }

}