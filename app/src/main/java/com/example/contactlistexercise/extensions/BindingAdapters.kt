package com.example.contactlistexercise.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.BindingAdapter

@BindingAdapter("phoneMaskWithCountryCode")
fun bindPhoneMaskCustomFormat(editText: EditText, maskEnabled: Boolean) {
    if (maskEnabled) {
        val countryCode = "+38-"
        var isEditing = false

        editText.setText(countryCode)
        editText.setSelection(countryCode.length)

        editText.addTextChangedListener(object : TextWatcher {
            private val numericRegex = Regex("[^0-9]")

            override fun afterTextChanged(s: Editable?) {
                if (isEditing) {
                    return
                }

                isEditing = true

                s?.let {
                    val currentText = it.toString()

                    if (!currentText.startsWith(countryCode)) {
                        editText.setText(countryCode)
                        editText.setSelection(countryCode.length)
                    } else {
                        val rawString = numericRegex.replace(currentText.removePrefix(countryCode), "")
                        val formattedString = formatCustomPhoneNumber(rawString, countryCode)

                        editText.setText(formattedString)
                        editText.setSelection(formattedString.length)
                    }
                }

                isEditing = false
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }
}

private fun formatCustomPhoneNumber(number: String, countryCode: String): String {
    val trimmed = if (number.length >= 10) number.substring(0..9) else number
    val stringBuilder = StringBuilder(countryCode)

    if (trimmed.isNotEmpty()) stringBuilder.append("(")
    for (i in trimmed.indices) {
        if (i == 3) stringBuilder.append(")-")
        if (i == 6) stringBuilder.append("-")
        stringBuilder.append(trimmed[i])
    }

    return stringBuilder.toString()
}
