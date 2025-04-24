package com.example.contacts.screens.contacts

import androidx.recyclerview.widget.DiffUtil
import com.example.contacts.model.Contact

object ContactDiffCallback : DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.email == newItem.email
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem == newItem
    }
}
