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
        viewModelScope.launch(Dispatchers.IO) {
            if (name.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty()) {
                repository.upsert(ContactDb(name = name, phone = phone, email = email, dateCreate = dateCreate, dateUpdate = null))
                _state.postValue(AddState.Success("New contact was successfully added!"))
            } else {
                _state.postValue(AddState.Error("All fields must be filled"))
            }
        }
    }

    fun clearState() {
        _state.value = AddState.Empty
    }
}