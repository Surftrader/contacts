package com.example.contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts.data.DataSource
import com.example.contacts.data.InternDataSource
import com.example.contacts.databinding.ContactItemBinding
import com.example.contacts.model.Contact

class ContactAdapter : RecyclerView.Adapter<ContactAdapter.ContactHolder>() {

    private val dataSource: DataSource<Contact> = InternDataSource()
    private val contactList: List<Contact> = dataSource.getContacts()

    class ContactHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = ContactItemBinding.bind(item)

        fun bind(contact: Contact) = with(binding) {
            photoContact.setImageResource(contact.imageId)
            nameContact.text = buildString {
                append(contact.firstName)
                append(" ")
                append(contact.lastName)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        return ContactHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        holder.bind(contactList[position])
    }
}
