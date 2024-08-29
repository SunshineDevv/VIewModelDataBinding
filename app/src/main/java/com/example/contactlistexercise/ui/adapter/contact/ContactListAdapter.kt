package com.example.contactlistexercise.ui.adapter.contact

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactlistexercise.databinding.ItemContactBinding
import com.example.contactlistexercise.ui.fragment.contact.ContactModel

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
        val contactName = contact.name
        val contactPhone = contact.phone
        val contactEmail = contact.email

        holder.binding.textViewName.text = contactName
        holder.binding.textViewNumber.text = contactPhone
        holder.binding.textViewPost.text = contactEmail

        holder.itemView.setOnClickListener {
            itemClickedListener.onClickedItem(position)
        }

        holder.itemView.setOnLongClickListener {
            itemClickedListener.onLongClickedItem(contact, position)
            true
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateContactList(newContactList: List<ContactModel>) {
        contactList = newContactList
        notifyDataSetChanged()
    }

    interface RecyclerItemClicked{
        fun onClickedItem(index: Int)
        fun onLongClickedItem(contact: ContactModel, position: Int)
    }
}