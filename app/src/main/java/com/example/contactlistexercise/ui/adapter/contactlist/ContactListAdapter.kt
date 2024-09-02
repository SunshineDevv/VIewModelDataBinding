package com.example.contactlistexercise.ui.adapter.contactlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactlistexercise.database.model.ContactDb
import com.example.contactlistexercise.databinding.ItemContactBinding

class ContactListAdapter (private var contactList: List<ContactDb>, private val itemClickedListener: RecyclerItemClicked):
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
    fun updateContactList(newContactList: List<ContactDb>) {
        contactList = newContactList
        notifyDataSetChanged()
    }

    interface RecyclerItemClicked{
        fun onClickedItem(contact: ContactDb)
        fun onLongClickedItem(contact: ContactDb)
    }
}