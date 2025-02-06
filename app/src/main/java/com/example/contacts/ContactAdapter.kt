package com.example.contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts.databinding.ContactItemBinding
import com.example.contacts.model.Contact

class ContactAdapter(
    private val onClickDelete: (Int) -> Unit
) : ListAdapter<Contact, ContactAdapter.ContactHolder>(ContactDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        return ContactHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.contact_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    private class ContactDiffCallBack : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean =
            oldItem.email == newItem.email

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean =
            oldItem == newItem
    }

    inner class ContactHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = ContactItemBinding.bind(item)

        fun bind(contact: Contact, index: Int) = with(binding) {
            photoContact.setImageResource(contact.imageId)
            "${contact.firstName} ${contact.lastName}".also { nameContact.text = it }
            icTrash.setOnClickListener { onClickDelete(index) }
        }
    }
}
