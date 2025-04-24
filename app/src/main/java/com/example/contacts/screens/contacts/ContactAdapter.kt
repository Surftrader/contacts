package com.example.contacts.screens.contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.contacts.R
import com.example.contacts.model.Contact

class ContactAdapter(
    private val onDeleteClick: (Contact, Int) -> Unit
) : ListAdapter<Contact, ContactViewHolder>(ContactDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.contact_item, parent, false),
            onDeleteClick
        )
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = getItem(position)
        holder.bind(contact, position)
    }
}
