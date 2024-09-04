package com.example.contactlistexercise.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.contactlistexercise.database.model.ContactDb

@Dao
interface ContactDao {

    @Query("SELECT * FROM contacts")
    fun getAllContacts() : LiveData<List<ContactDb>>

    @Upsert
    suspend fun upsertContact(contactDb: ContactDb)

    @Delete
    suspend fun deleteContact(contactDb: ContactDb)

    @Query("UPDATE contacts set contact_name = :name, contact_phone = :phone, contact_email = :email, date_update = :updateDate where id = :id")
    suspend fun updateContact(id: Long?, name: String?, phone: String?, email: String?, updateDate: Long?)
}