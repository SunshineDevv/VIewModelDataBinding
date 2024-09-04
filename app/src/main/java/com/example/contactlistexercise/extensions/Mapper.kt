package com.example.contactlistexercise.extensions

import com.example.contactlistexercise.database.model.ContactDb
import com.example.contactlistexercise.ui.fragment.contactlist.ContactModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun ContactDb.toContactsUIModel(): ContactModel{
    return ContactModel(id = id, name = name, phone = phone, email = email, dateUpdate = dateUpdate?.toFormattedDate(), dateCreate = dateCreate?.toFormattedDate())
}

fun ContactModel.toContactsDbModel(): ContactDb{
    return ContactDb(id = id, name = name, phone = phone, email = email, dateCreate = dateCreate?.toDateInMillis(), dateUpdate = dateUpdate?.toDateInMillis())
}

fun List<ContactModel>.toContactDBModelList(): List<ContactDb>{
    return this.map {it.toContactsDbModel()}
}

fun List<ContactDb>.toContactModelList(): List<ContactModel>{
    return this.map {it.toContactsUIModel()}
}

fun Long.toFormattedDate(): String {
    val date = Date(this)
    val format = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
    return format.format(date)
}

fun String.toDateInMillis(): Long {
    val format = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
    val date = format.parse(this)
    return date?.time ?: 0L
}