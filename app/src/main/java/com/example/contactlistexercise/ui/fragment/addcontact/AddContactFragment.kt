package com.example.contactlistexercise.ui.fragment.addcontact

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.contactlistexercise.R
import com.example.contactlistexercise.databinding.FragmentAddContactBinding
import java.time.LocalDate
import java.util.Locale


class AddContactFragment : Fragment() {

    private var binding: FragmentAddContactBinding? = null

    private val addContactViewModel: AddContactViewModel by activityViewModels()

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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addContactViewModel.onStart(requireContext())

        initObservers()

        binding?.buttonAddContact?.setOnClickListener {
            val dateCreate = System.currentTimeMillis()
            addContactViewModel.addContact(binding?.editTextName?.text.toString(),
                binding?.editTextPhone?.text.toString(),
                binding?.editTextTextPostalAddress?.text.toString(),dateCreate)
        }

        binding?.buttonToContactList?.setOnClickListener {
            findNavController().navigate(R.id.navigateAddContact_toContactList)
        }
    }

    private fun initObservers() {
        addContactViewModel.state.observe(viewLifecycleOwner){ state ->
            when(state) {
                is AddState.Error -> {
                    Toast.makeText(requireContext(), state.errorMsg, Toast.LENGTH_LONG)
                        .show()
                    addContactViewModel.clearState()
                }

                is AddState.Success -> {
                    Toast.makeText(requireContext(), state.successMsg, Toast.LENGTH_LONG)
                        .show()
                    addContactViewModel.clearState()
                }

                else -> {  }
            }
        }
    }

}
