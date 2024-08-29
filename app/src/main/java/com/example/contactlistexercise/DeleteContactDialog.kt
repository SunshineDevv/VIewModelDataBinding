package com.example.contactlistexercise

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import com.example.contactlistexercise.databinding.CustomDialogDeleteContactBinding

class DeleteContactDialog(context: Context): Dialog(context) {

    private var binding: CustomDialogDeleteContactBinding? = null

    var onSubmitButtonClick: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setCancelable(false)

        binding = CustomDialogDeleteContactBinding.inflate(layoutInflater)
        binding?.root?.let{ setContentView(it) }

        binding?.buttonCancel?.setOnClickListener {
            dismiss()
        }

        binding?.buttonSubmit?.setOnClickListener {
            onSubmitButtonClick?.invoke()
            dismiss()
        }
    }
}