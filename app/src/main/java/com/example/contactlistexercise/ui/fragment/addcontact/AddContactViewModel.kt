package com.example.contactlistexercise.ui.fragment.addcontact

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactlistexercise.database.AppDatabase
import com.example.contactlistexercise.database.model.ContactDb
import com.example.contactlistexercise.database.repository.ContactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class AddContactViewModel : ViewModel() {

    private lateinit var repository: ContactRepository

    private val _state = MutableLiveData<AddState>()
    val state: LiveData<AddState> = _state

    val name = MutableLiveData<String>()
    val phone = MutableLiveData<String>()
    val email = MutableLiveData<String>()

    fun onStart(context: Context) {
        val contactDao = AppDatabase.getDatabase(context).getContactDao()
        repository = ContactRepository(contactDao)
    }

    fun addContact(name: String, phone: String, email: String, dateCreate: Long) {
        if (!validatePhoneNumber(phone)) {
            _state.postValue(AddState.Error("Number must be input correctly!"))
            return
        } else if (!validateEmailAddress(email)) {
            _state.postValue(AddState.Error("Email must be input correctly!"))
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            if (name.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty()) {
                repository.upsert(
                    ContactDb(
                        name = name,
                        phone = phone.replace(Regex("[^+0-9]"), ""),
                        email = email,
                        dateCreate = dateCreate,
                        dateUpdate = null
                    )
                )
                _state.postValue(AddState.Success("New contact was successfully added!"))
            } else {
                _state.postValue(AddState.Error("All fields must be filled"))
            }
        }
    }

    fun clearState() {
        _state.value = AddState.Empty
    }

    private fun validateEmailAddress(email: String): Boolean {
        val regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
        val pattern = Pattern.compile(regex)
        return pattern.matcher(email).matches()
    }

    private fun validatePhoneNumber(phoneNumber: String): Boolean {
        val regex = "^\\+38-\\(\\d{3}\\)-\\d{3}-\\d{4}$"
        val pattern = Pattern.compile(regex)
        return pattern.matcher(phoneNumber).matches()
    }
}