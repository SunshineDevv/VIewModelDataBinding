package com.example.contactlistexercise.extensions

import com.example.contactlistexercise.database.model.ContactDb
import com.example.contactlistexercise.ui.fragment.contactlist.ContactModel


fun ContactDb.toContactsUIModel(): ContactModel{
    return ContactModel(id, name ?: "", phone ?: "", email ?: "")
}

fun ContactModel.toContactsDbModel(): ContactDb{
    return ContactDb(id = id, name = name, phone = phone, email = email)
}

fun List<ContactModel>.toContactDBModelList(): List<ContactDb>{
    return this.map {
        ContactDb(id = it.id, name = it.name, phone = it.phone, email = it.email)
    }
}

fun List<ContactDb>.toContactModelList(): List<ContactModel>{
    return this.map {
        ContactModel(id = it.id, name = it.name, phone = it.phone, email = it.email)
    }
}