package com.example.contactlistexercise.ui.fragment.addcontact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.contactlistexercise.ui.fragment.contact.ContactModel

class AddContactViewModel: ViewModel() {

    private val _state = MutableLiveData<AddState>()
    val state : LiveData<AddState> = _state

    val name = MutableLiveData<String>()
    val phone = MutableLiveData<String>()
    val email = MutableLiveData<String>()

    fun addUser(name: String, phone: String, email: String) {
        _state.value = AddState.Loading
        if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            _state.value = AddState.Error("All fields must be filled")
        } else {
            _state.value = AddState.Success(ContactModel(name, phone, email))
        }
    }

    fun clearState(){
        _state.value = AddState.Empty
    }
}