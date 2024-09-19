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
        var hasCountryCode = false // Флаг для отслеживания состояния кода страны

        editText.addTextChangedListener(object : TextWatcher {
            private val numericRegex = Regex("[^0-9]")

            override fun afterTextChanged(s: Editable?) {
                if (isEditing) {
                    return
                }

                isEditing = true

                s?.let {
                    val currentText = it.toString()

                    // Если поле полностью пустое (все символы удалены), сбрасываем флаг и очищаем поле
                    if (currentText.isEmpty()) {
                        hasCountryCode = false
                    }

                    // Восстанавливаем код страны, если он был частично удален
                    if (hasCountryCode && !currentText.startsWith(countryCode)) {
                        editText.setText(countryCode)
                        editText.setSelection(countryCode.length)
                    }
                    // Если пользователь вводит символы, и код страны не был добавлен, добавляем код страны
                    else if (currentText.isNotEmpty() && !hasCountryCode) {
                        hasCountryCode = true
                        val newText = countryCode + currentText
                        editText.setText(newText)
                        editText.setSelection(newText.length)
                    }
                    // Если пользователь удаляет символы вплоть до кода страны, удаляем код страны целиком
                    else if (currentText.length <= countryCode.length && hasCountryCode) {
                        hasCountryCode = false
                        editText.setText("") // Очищаем поле целиком
                    }
                    // Обрабатываем форматирование номера, если код страны уже есть
                    else if (hasCountryCode && currentText.startsWith(countryCode)) {
                        val rawString = numericRegex.replace(currentText.removePrefix(countryCode), "")
                        val formattedString = formatCustomPhoneNumber(rawString, countryCode)

                        // Проверка на полное удаление кода страны
                        if (rawString.isEmpty()) {
                            editText.setText("")
                            hasCountryCode = false
                        } else {
                            editText.setText(formattedString)
                            editText.setSelection(formattedString.length)
                        }
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

    // Форматируем номер в виде (XXX) XXX-XXXX
    if (trimmed.isNotEmpty()) stringBuilder.append("(")
    for (i in trimmed.indices) {
        if (i == 3) stringBuilder.append(")-")
        if (i == 6) stringBuilder.append("-")
        stringBuilder.append(trimmed[i])
    }

    return stringBuilder.toString()
}
