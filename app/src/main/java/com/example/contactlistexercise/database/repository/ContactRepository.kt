package com.example.contactlistexercise.database.repository

import androidx.lifecycle.LiveData
import com.example.contactlistexercise.database.dao.ContactDao
import com.example.contactlistexercise.database.model.ContactDb

class ContactRepository(private val contactDao: ContactDao) {

    val allContacts: LiveData<List<ContactDb>> = contactDao.getAllContacts()

    suspend fun upsert(contact: ContactDb){
        contactDao.upsertContact(contact)
    }

    suspend fun delete(contact: ContactDb){
        contactDao.deleteContact(contact)
    }

    suspend fun update(id: Long?, name: String?, phone: String?, email: String?, dateUpdate: Long?){
        contactDao.updateContact(id, name, phone, email, dateUpdate)
    }
}