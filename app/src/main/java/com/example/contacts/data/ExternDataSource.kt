package com.example.contacts.data

import com.example.contacts.model.Contact

class ExternDataSource : DataSource {
    override fun getContacts(): List<Contact> {
        return emptyList()
    }

    fun addContact(contact: Contact) {
        // TODO
    }

    fun removeContact(contact: Contact) {
        // TODO
    }
}
