package com.example.contactlistexercise

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddContactViewModel: ViewModel() {
    val name = MutableLiveData<String>()
    val phone = MutableLiveData<String>()
    val email = MutableLiveData<String>()
}