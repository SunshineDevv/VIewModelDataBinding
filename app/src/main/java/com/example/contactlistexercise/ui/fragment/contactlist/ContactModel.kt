package com.example.contactlistexercise.ui.fragment.contactlist

data class ContactModel(
    val id: Long,
    val name: String?,
    val phone: String?,
    val email: String?,
    val dateCreate: String?,
    val dateUpdate: String?
)