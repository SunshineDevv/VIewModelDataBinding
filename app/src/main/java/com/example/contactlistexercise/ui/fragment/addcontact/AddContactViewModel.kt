package com.example.contactlistexercise.ui.fragment.addcontact

import android.provider.ContactsContract.CommonDataKinds.Phone
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.contactlistexercise.ui.fragment.contactlist.ContactModel

class AddContactViewModel: ViewModel() {

    private val _state = MutableLiveData<AddState>()
    val state: LiveData<AddState> = _state

    val name = MutableLiveData<String>()
    val phone = MutableLiveData<String>()
    val email = MutableLiveData<String>()

    fun addContact(name: String, phone: String, email: String){
        if(name.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty()){
            _state.value = AddState.Success(ContactModel(name,phone,email))
        } else {
            _state.value = AddState.Error("All fields must be filled")
        }
    }

    fun clearState(){
        _state.value = AddState.Empty
    }
}