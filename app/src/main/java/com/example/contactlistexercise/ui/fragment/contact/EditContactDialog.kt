package com.example.contactlistexercise.ui.fragment.contact

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.example.contactlistexercise.databinding.CustomDialogUpdateContactBinding

class EditContactDialog(context: Context, private val index: Int, private val contactViewModel: ContactListViewModel): Dialog(context) {

    private var binding: CustomDialogUpdateContactBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setCancelable(false)

        binding = CustomDialogUpdateContactBinding.inflate(layoutInflater)
        binding?.root?.let{ setContentView(it) }

//        binding?.editTextName?.setText(contact.name)
//        binding?.editTextPhone?.setText(contact.phone)
//        binding?.editTextEmail?.setText(contact.email)

        binding?.buttonCancel?.setOnClickListener {
            dismiss()
        }

        binding?.buttonUpdate?.setOnClickListener {
            val newName = binding?.editTextName?.text
            val newPhone = binding?.editTextPhone?.text
            val newEmail = binding?.editTextEmail?.text

            if (newName.toString().isNotEmpty() && newPhone.toString().isNotEmpty() && newEmail.toString().isNotEmpty()) {
                val updatedContact = ContactModel(name = newName.toString(), phone = newPhone.toString(), email = newEmail.toString())
                contactViewModel.updateContact(index, updatedContact)
            } else {
                Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show()
            }
            dismiss()
        }
    }

}