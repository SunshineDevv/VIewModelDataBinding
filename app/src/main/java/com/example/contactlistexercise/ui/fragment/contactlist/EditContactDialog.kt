package com.example.contactlistexercise.ui.fragment.contactlist

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.example.contactlistexercise.databinding.CustomDialogUpdateContactBinding
import com.example.contactlistexercise.extensions.toFormattedDate

class EditContactDialog(context: Context, private val contact: ContactModel, private val contactViewModel: ContactListViewModel): Dialog(context) {

    private var binding: CustomDialogUpdateContactBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setCancelable(false)

        binding = CustomDialogUpdateContactBinding.inflate(layoutInflater)
        binding?.root?.let{ setContentView(it) }

        binding?.editTextName?.setText(contact.name)
        binding?.editTextPhone?.setText(contact.phone)
        binding?.editTextEmail?.setText(contact.email)

        binding?.buttonCancel?.setOnClickListener {
            dismiss()
        }

        binding?.buttonUpdate?.setOnClickListener {
            val newName = binding?.editTextName?.text.toString()
            val newPhone = binding?.editTextPhone?.text.toString()
            val newEmail = binding?.editTextEmail?.text.toString()

            val dateUpdate = System.currentTimeMillis().toFormattedDate()

            contactViewModel.updateContact(contact.id, newName, newPhone, newEmail, dateUpdate)

            dismiss()
        }
    }

}