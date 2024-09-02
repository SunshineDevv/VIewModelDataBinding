package com.example.contactlistexercise.ui.fragment.contactlist

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactlistexercise.database.AppDatabase
import com.example.contactlistexercise.database.model.ContactDb
import com.example.contactlistexercise.database.repository.ContactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactListViewModel(application: Application): AndroidViewModel(application) {

    private val repository: ContactRepository

    val contacts: LiveData<List<ContactDb>>

    init {
        val contactDao = AppDatabase.getDatabase(application).getContactDao()
        repository = ContactRepository(contactDao)
        contacts = repository.allContacts
    }

    fun addContact(name: String, phone: String, email: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.upsert(ContactDb(name = name, phone = phone, email = email))
        }
    }

    fun deleteContact(contactDb: ContactDb){
        viewModelScope.launch(Dispatchers.IO){
            repository.delete(contactDb)
        }
    }

    fun updateContact(contactDb: ContactDb, position: Long){
        viewModelScope.launch(Dispatchers.IO){
            repository.update(position, contactDb.name, contactDb.phone, contactDb.email)

        }
    }


//    private val _contacts = MutableLiveData<MutableList<ContactModel>>()
//    val contacts: LiveData<MutableList<ContactModel>> = _contacts
//
//    fun addContact(name: String, phone: String, email: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val currentList = _contacts.value ?: mutableListOf()
//            currentList.add(ContactModel(name, phone, email))
//            _contacts.postValue(currentList)
//        }
//    }
//
//    fun updateContact(position: Int, newContact: ContactModel){
//        viewModelScope.launch(Dispatchers.IO) {
//            val currentList = _contacts.value ?: return@launch
//            if (position >= 0) {
//                currentList[position] = newContact
//                _contacts.postValue(currentList)
//            }
//        }
//    }
//
//    fun deleteContact(contact: ContactModel, position: Int) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val currentList = _contacts.value ?: return@launch
//            if (currentList.contains(contact)) {
//                currentList.removeAt(position)
//                _contacts.postValue(currentList)
//            }
//        }
//    }
}