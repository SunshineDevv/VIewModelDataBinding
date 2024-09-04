package com.example.contactlistexercise.ui.adapter.contactlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactlistexercise.databinding.ItemContactBinding
import com.example.contactlistexercise.ui.fragment.contactlist.ContactModel

class ContactListAdapter (private var contactList: List<ContactModel>, private val itemClickedListener: RecyclerItemClicked):
    RecyclerView.Adapter<ContactListAdapter.ContactListViewHolder>() {

    class ContactListViewHolder(val binding: ItemContactBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactListViewHolder {
        val binding = ItemContactBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ContactListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: ContactListViewHolder, position: Int) {
        val contact = contactList[position]
        val contactName = contactList[position].name
        val contactPhone = contactList[position].phone
        val contactEmail = contactList[position].email
        holder.binding.textViewName.text = contactName
        holder.binding.textViewNumber.text = contactPhone
        holder.binding.textViewPost.text = contactEmail

        holder.itemView.setOnClickListener {
            itemClickedListener.onClickedItem(contact)
        }

        holder.itemView.setOnLongClickListener {
            itemClickedListener.onLongClickedItem(contact)
            true
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateContactList(newContactList: List<ContactModel>) {
        contactList = newContactList
        notifyDataSetChanged()
    }

    interface RecyclerItemClicked{
        fun onClickedItem(contact: ContactModel)
        fun onLongClickedItem(contact: ContactModel)
    }
}