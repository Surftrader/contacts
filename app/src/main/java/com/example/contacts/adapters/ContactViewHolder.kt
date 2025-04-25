package com.example.contacts.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts.databinding.ContactItemBinding
import com.example.contacts.model.Contact

class ContactViewHolder(
    itemView: View,
    private val onDeleteClick: (Contact, Int) -> Unit
) : RecyclerView.ViewHolder(itemView) {
    private val binding = ContactItemBinding.bind(itemView)

    fun bind(contact: Contact, position: Int) = with(binding) {
        photoContact.setImageResource(contact.imageId)
        nameContact.text = String.format("%S %S", contact.firstName, contact.lastName)
        contact.profession.also { professionContact.text = it }
        icTrash.setOnClickListener { onDeleteClick(contact, position) }
    }
}