package com.example.contactlistexercise.ui.fragment.addcontact

sealed class AddState {
    data class Success(val successMsg: String): AddState()
    data class Error(val errorMsg: String?): AddState()
    data class ErrorLength(val errorMsg: String?): AddState()
    data object Empty: AddState()
}