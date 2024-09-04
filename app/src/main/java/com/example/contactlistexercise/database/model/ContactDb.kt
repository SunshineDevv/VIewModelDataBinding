package com.example.contactlistexercise.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class ContactDb(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "contact_name") val name: String?,
    @ColumnInfo(name = "contact_phone") val phone: String?,
    @ColumnInfo(name = "contact_email") val email: String?,
    @ColumnInfo(name = "date_create") val dateCreate: Long?,
    @ColumnInfo(name = "date_update") val dateUpdate: Long?
)
