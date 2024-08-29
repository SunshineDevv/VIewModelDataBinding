package com.example.contactlistexercise.ui.fragment.addcontact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.contactlistexercise.R
import com.example.contactlistexercise.databinding.FragmentAddContactBinding


class AddContactFragment : Fragment() {

    private var binding: FragmentAddContactBinding? = null

    private val addContactViewModel by activityViewModels<AddContactViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_contact, container, false)
        binding?.viewmodel = addContactViewModel
        binding?.lifecycleOwner = viewLifecycleOwner
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        binding?.buttonAddContact?.setOnClickListener {
            addContactViewModel.addUser(binding?.editTextName?.text.toString(), binding?.editTextPhone?.text.toString(), binding?.editTextTextPostalAddress?.text.toString())
        }

        binding?.buttonToContactList?.setOnClickListener {
            findNavController().navigate(R.id.navigateAddContact_toContactList)
        }
    }

    private fun initObservers() {
        addContactViewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                AddState.Loading -> {

                }
                is AddState.Error -> {
                    Toast.makeText(requireContext(), state.errorMsg, Toast.LENGTH_LONG).show()

                }
                is AddState.Success -> {
                    val action = AddContactFragmentDirections.navigateAddContactToContactList(
                        state.contact.name,
                        state.contact.phone,
                        state.contact.email
                    )
                    findNavController().navigate(action)
                    addContactViewModel.clearState()
                }
                else -> {

                }
            }
        }
    }

}
