package com.kozin.notesnotes.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kozin.notesnotes.R
import com.kozin.notesnotes.model.Notes
import com.kozin.notesnotes.viewmodel.NotesViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mNotesViewModel: NotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mNotesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

        view.update_header_et.setText(args.currentNotes.header)
        view.update_descr_et.setText(args.currentNotes.desc)

        view.update_button.setOnClickListener {
            updateItem()
        }

        // Add menu
        setHasOptionsMenu(true)

        return view
    }

    fun updateItem() {
        val header = update_header_et.text.toString()
        val descr = update_descr_et.text.toString()

        if (inputCheck(header)
            && header.length <= 30
            && descr.isNotEmpty()
            && descr.length <= 750) {
            // Create notes obj
            val updatedNotes = Notes(args.currentNotes.id, header, descr)

            // Update current note
            mNotesViewModel.updateNotes(updatedNotes)
            Toast.makeText(requireContext(), "Successfully updated", Toast.LENGTH_SHORT).show()

            // Navigate back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

    fun inputCheck(header: String): Boolean {
        return !(TextUtils.isEmpty(header))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteNotes()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteNotes() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mNotesViewModel.deleteNotes(args.currentNotes)
            Toast.makeText(requireContext(), "Successfully deleted", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }

        builder.setNegativeButton("No") { _, _ ->

        }

        builder.setTitle("Delete ${args.currentNotes.header}?")
        builder.setMessage("You really want to delete ${args.currentNotes.header}?")
        builder.create().show()

    }

}