package com.example.contactlistexercise.ui.fragment.addcontact

import com.example.contactlistexercise.ui.fragment.contact.ContactModel

sealed class AddState {
    data class Success(val contact: ContactModel): AddState()
    data class Error(val errorMsg: String?): AddState()
    data object Empty: AddState()
    data object Loading: AddState()
}