package com.example.contactlistexercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ContactListViewModel: ViewModel() {
    private val _contacts = MutableLiveData<MutableList<ContactModel>>()
    val contacts: LiveData<MutableList<ContactModel>> = _contacts

    fun addContact(name: String, phone: String, email: String) {
        val currentList= _contacts.value ?: mutableListOf()
        currentList.add(ContactModel(name,phone,email))
        _contacts.value = currentList
    }

    fun updateContact(oldContact: ContactModel, newContact: ContactModel){
        val currentList = _contacts.value ?: return
        val index = currentList.indexOf(oldContact)
        if(index >= 0){
            currentList[index] = newContact
            _contacts.value = currentList
        }
    }
}