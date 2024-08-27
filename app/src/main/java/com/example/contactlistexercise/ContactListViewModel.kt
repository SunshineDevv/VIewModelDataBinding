package com.example.contactlistexercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ContactListViewModel: ViewModel() {
    private val _contacts = MutableLiveData<List<ContactModel>>()
    val contacts: LiveData<List<ContactModel>> = _contacts

    fun addTask(name: String, phone: String, email: String) {
        val currentList = _contacts.value ?: listOf()
        val updatedList = currentList + ContactModel(name,phone,email)
        _contacts.value = updatedList
    }
}