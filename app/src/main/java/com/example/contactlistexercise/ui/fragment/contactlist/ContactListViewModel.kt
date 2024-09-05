package com.example.contactlistexercise.ui.fragment.contactlist

import android.content.Context
import android.provider.ContactsContract.CommonDataKinds.Phone
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
import com.example.contactlistexercise.extensions.toDateInMillis
import com.example.contactlistexercise.ui.fragment.addcontact.AddState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactListViewModel : ViewModel() {

    private lateinit var repository: ContactRepository

    private val _contacts = MutableLiveData<List<ContactModel>>(emptyList())
    val contacts: LiveData<List<ContactModel>> = _contacts

    private val _state = MutableLiveData<AddState>()
    val state: LiveData<AddState> = _state

    fun onStart(context: Context) {
        val contactDao = AppDatabase.getDatabase(context).getContactDao()
        repository = ContactRepository(contactDao)
        observeLiveData(repository.allContacts,::handleContactsChanged)
    }

    private fun handleContactsChanged(contactDbs: List<ContactDb>) {
        _contacts.postValue(contactDbs.sortedByDescending {
            it.dateUpdate ?: it.dateCreate
        }.toContactModelList())
    }


    fun deleteContact(contact: ContactModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(contact.toContactsDbModel())
        }
    }

    fun updateContact(id: Long, newName: String, newPhone: String, newEmail: String, dateUpdate: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (newName.isNotEmpty() && newPhone.isNotEmpty() && newEmail.isNotEmpty() && dateUpdate.isNotEmpty()) {
                repository.update(id, newName, newPhone, newEmail, dateUpdate.toDateInMillis())
                _state.postValue(AddState.Success("Contact was updated!"))
            } else {
                _state.postValue(AddState.Error("All values must be filled"))
            }
        }
    }
}