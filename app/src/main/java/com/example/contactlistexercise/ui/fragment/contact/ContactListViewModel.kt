package com.example.contactlistexercise.ui.fragment.contact

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactListViewModel: ViewModel() {
    private val _contacts = MutableLiveData<MutableList<ContactModel>>()
    val contacts: LiveData<MutableList<ContactModel>> = _contacts

    fun addContact(name: String, phone: String, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentList = _contacts.value ?: mutableListOf()
            currentList.add(ContactModel(name, phone, email))
            _contacts.postValue(currentList)
        }
    }

    fun updateContact(index: Int, newContact: ContactModel){
        viewModelScope.launch(Dispatchers.IO) {
            val currentList = _contacts.value ?: return@launch
            Log.i("CHECK_LIST", "updateContact old: ${_contacts.value} and index ${index}")
            if (index >= 0) {
                currentList[index] = newContact
                _contacts.postValue(currentList)
                Log.i("CHECK_LIST", "updateContact new: ${_contacts.value} and index ${index}")
            }
        }
    }

    fun deleteContact(contact: ContactModel, position: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentList = _contacts.value ?: return@launch
            if (currentList.contains(contact)) {
                currentList.removeAt(position)
                _contacts.postValue(currentList)
            }
        }
    }
}