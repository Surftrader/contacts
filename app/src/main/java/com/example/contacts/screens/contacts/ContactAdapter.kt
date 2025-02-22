package com.example.contacts.screens.contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts.R
import com.example.contacts.databinding.ContactItemBinding
import com.example.contacts.model.Contact

class ContactAdapter(private val onDeleteClick: (Contact, Int) -> Unit) :
    ListAdapter<Contact, ContactAdapter.ContactViewHolder>((DIFF_CALLBACK)) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.contact_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = getItem(position)
        holder.bind(contact, position)
    }

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ContactItemBinding.bind(itemView)

        fun bind(contact: Contact, position: Int) = with(binding) {
            photoContact.setImageResource(contact.imageId)
            "${contact.firstName} ${contact.lastName}".also { nameContact.text = it }
            contact.profession.also { professionContact.text = it }
            icTrash.setOnClickListener { onDeleteClick(contact, position) }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Contact>() {
            override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return oldItem.email == newItem.email
            }

            override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return oldItem == newItem
            }
        }
    }
}
