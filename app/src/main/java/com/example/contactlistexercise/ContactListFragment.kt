package com.example.contactlistexercise

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactlistexercise.databinding.FragmentContactListBinding


class ContactListFragment : Fragment() {
    private var binding: FragmentContactListBinding? = null

    private var contactList: List<ContactModel> = listOf()

    private lateinit var adapter:ContactListAdapter

    private val contactViewModel:ContactListViewModel by activityViewModels()

    val args: ContactListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            val name = args.name
            val phone = args.phone
            val email = args.email

            contactViewModel.addTask(name, phone, email)
        }

        adapter = ContactListAdapter(contactList)

        binding?.recyclerViewContact?.layoutManager = LinearLayoutManager(requireContext())
        binding?.recyclerViewContact?.adapter = adapter

        contactViewModel.contacts.observe(viewLifecycleOwner){ contacts ->
            adapter.updateContactList(contacts)
        }

        binding?.buttonBack?.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.navigateContactList_toAddContact)
        }
    }
}