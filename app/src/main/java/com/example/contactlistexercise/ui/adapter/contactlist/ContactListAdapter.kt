package com.example.contactlistexercise.ui.adapter.contactlist

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactlistexercise.R
import com.example.contactlistexercise.databinding.ItemContactBinding
import com.example.contactlistexercise.extensions.toDateInMillis
import com.example.contactlistexercise.ui.fragment.contactlist.ContactModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ContactListAdapter(
    private var contactList: List<ContactModel>,
    private val itemClickedListener: RecyclerItemClicked,
    private val context: Context
) :
    RecyclerView.Adapter<ContactListAdapter.ContactListViewHolder>() {

    class ContactListViewHolder(val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactListViewHolder {
        val binding = ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ContactListViewHolder, position: Int) {
        val contact = contactList[position]

        val contactName = contactList[position].name
        val contactPhone = contactList[position].phone
        val contactEmail = contactList[position].email

        holder.binding.textViewName.text = contactName
        holder.binding.textViewNumber.text = contactPhone
        holder.binding.textViewPost.text = contactEmail

        holder.binding.textDateCreate.text = context.getString(R.string.date_create,
            contact.dateCreate?.let { formatDate(it) })

        if (contact.dateUpdate == null) {
            holder.binding.textDateUpdate.visibility = View.GONE
        } else {
            holder.binding.textDateUpdate.visibility = View.VISIBLE
            holder.binding.textDateUpdate.text = context.getString(R.string.date_update, formatDate(contact.dateUpdate))
        }

        holder.itemView.setOnClickListener {
            itemClickedListener.onClickedItem(contact)
        }

        holder.itemView.setOnLongClickListener {
            itemClickedListener.onLongClickedItem(contact)
            true
        }
    }

    private fun formatDate(updatingDate: String): String{
        val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
        val formattedDateCreate = sdf.format(Date(updatingDate.toDateInMillis()))
        return formattedDateCreate
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateContactList(newContactList: List<ContactModel>) {
        contactList = newContactList
        notifyDataSetChanged()
    }

    interface RecyclerItemClicked {
        fun onClickedItem(contact: ContactModel)
        fun onLongClickedItem(contact: ContactModel)
    }
}