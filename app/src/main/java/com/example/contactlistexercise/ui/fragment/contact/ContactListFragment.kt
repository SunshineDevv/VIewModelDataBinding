package com.example.contactlistexercise.ui.fragment.contact

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactlistexercise.ui.adapter.contact.ContactListAdapter
import com.example.contactlistexercise.R
import com.example.contactlistexercise.databinding.FragmentContactListBinding

class ContactListFragment : Fragment(), ContactListAdapter.RecyclerItemClicked {
    private var binding: FragmentContactListBinding? = null

    private var contactList: List<ContactModel> = mutableListOf()

    private lateinit var adapter: ContactListAdapter

    private val contactViewModel: ContactListViewModel by activityViewModels()

    private val args: ContactListFragmentArgs by navArgs()

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

            if(name != "null" && phone != "null" && email != "null"){
                contactViewModel.addContact(name, phone, email)
            }
        }

        adapter = ContactListAdapter(contactList, this)

        binding?.recyclerViewContact?.layoutManager = LinearLayoutManager(requireContext())
        binding?.recyclerViewContact?.adapter = adapter

        contactViewModel.contacts.observe(viewLifecycleOwner){ contacts ->
            Log.i("MY_LOG", "onViewCreated: ${contacts}")
            adapter.updateContactList(contacts)
        }

        binding?.buttonBack?.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.navigateContactList_toAddContact)
        }
    }

    override fun onClickedItem(index: Int) {

        val dialog = EditContactDialog(requireContext(), index, contactViewModel)

        dialog.show()
        dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    override fun onLongClickedItem(contact: ContactModel, position: Int) {

        val dialog = DeleteContactDialog(requireContext())

        dialog.onSubmitButtonClick = {
            contactViewModel.deleteContact(contact, position)
            Toast.makeText(requireContext(), "Contact was successfully deleted!", Toast.LENGTH_LONG).show()
        }

        dialog.show()
        dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }
}