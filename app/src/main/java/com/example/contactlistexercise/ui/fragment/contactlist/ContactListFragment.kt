package com.example.contactlistexercise.ui.fragment.contactlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactlistexercise.R
import com.example.contactlistexercise.database.AppDatabase
import com.example.contactlistexercise.databinding.FragmentContactListBinding
import com.example.contactlistexercise.ui.adapter.contactlist.ContactListAdapter
import com.example.contactlistexercise.ui.fragment.addcontact.AddState

class ContactListFragment : Fragment(),  ContactListAdapter.RecyclerItemClicked{

    private var binding: FragmentContactListBinding? = null

    private var contactList: List<ContactModel> = listOf()

    private lateinit var adapter: ContactListAdapter

    private lateinit var database: AppDatabase

    private val contactViewModel: ContactListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contactViewModel.onStart(requireContext())

        initObservers()

        adapter = ContactListAdapter(contactList, this, requireContext())

        binding?.recyclerViewContact?.layoutManager = LinearLayoutManager(requireContext())
        binding?.recyclerViewContact?.adapter = adapter

        contactViewModel.contacts.observe(viewLifecycleOwner){ contacts ->
            adapter.updateContactList(contacts)
        }

        database = AppDatabase.getDatabase(requireContext())

        binding?.buttonBack?.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.navigateContactList_toAddContact)
        }
    }

    private fun initObservers(){
        contactViewModel.state.observe(viewLifecycleOwner){ state ->
            when(state){
                is AddState.Success -> {
                    Toast.makeText(requireContext(),state.successMsg,Toast.LENGTH_LONG).show()
                    contactViewModel.clearState()
                }
                is AddState.Error -> {
                    Toast.makeText(requireContext(),state.errorMsg,Toast.LENGTH_LONG).show()
                    contactViewModel.clearState()
                }
                is AddState.ErrorLength -> {
                    Toast.makeText(requireContext(), state.errorMsg,Toast.LENGTH_LONG).show()
                    contactViewModel.clearState()
                }
                else -> {}
            }

        }
    }

    override fun onClickedItem(contact: ContactModel) {
        val dialog = EditContactDialog(requireContext(), contact, contactViewModel)

        dialog.show()
        dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    override fun onLongClickedItem(contact: ContactModel) {

        val dialog = DeleteContactDialog(requireContext())

        dialog.onSubmitButtonClick = {
            contactViewModel.deleteContact(contact)
            Toast.makeText(requireContext(), "Contact was successfully deleted!", Toast.LENGTH_LONG).show()
        }

        dialog.show()
        dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }
}