package com.example.contactlistexercise

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.contactlistexercise.databinding.FragmentAddContactBinding


class AddContactFragment : Fragment() {

    private var binding: FragmentAddContactBinding? = null

    private val addContactViewModel: AddContactViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_contact, container, false)
        binding?.viewmodel = addContactViewModel
        binding?.lifecycleOwner = viewLifecycleOwner
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.buttonAddContact?.setOnClickListener {
            val name = addContactViewModel.name.value
            val phone = addContactViewModel.phone.value
            val email = addContactViewModel.email.value

            val action = AddContactFragmentDirections.navigateAddContactToContactList(name.toString(),phone.toString(),email.toString())
            Navigation.findNavController(view).navigate(action)
        }

    }

}
