package com.example.contactlistexercise.ui.fragment.contactlist

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactlistexercise.database.AppDatabase
import com.example.contactlistexercise.database.model.ContactDb
import com.example.contactlistexercise.database.repository.ContactRepository
import com.example.contactlistexercise.extensions.observeLiveData
import com.example.contactlistexercise.extensions.toContactModelList
import com.example.contactlistexercise.extensions.toContactsDbModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactListViewModel : ViewModel() {

    private lateinit var repository: ContactRepository

    private val _contacts = MutableLiveData<List<ContactModel>>(emptyList())
    val contacts: LiveData<List<ContactModel>> = _contacts

    fun onStart(context: Context) {
        val contactDao = AppDatabase.getDatabase(context).getContactDao()
        repository = ContactRepository(contactDao)
        observeLiveData(repository.allContacts,::handleContactsChanged)
    }

    private fun handleContactsChanged(contactDbs: List<ContactDb>) {
        _contacts.postValue(contactDbs.toContactModelList())
    }

    fun deleteContact(contact: ContactModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(contact.toContactsDbModel())
        }
    }

    fun updateContact(contact: ContactModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(contact.id, contact.name, contact.phone, contact.email)

        }
    }
}