package com.example.contacts.repository

import com.example.contacts.data.ExternDataSource
import com.example.contacts.data.InternDataSource
import com.example.contacts.model.Contact
import javax.inject.Inject

class DefaultContactRepository @Inject constructor(
    private val internDataSource: InternDataSource,
    private val externDataSource: ExternDataSource
) : ContactRepository {

    override fun getContacts(): List<Contact> {
        return internDataSource.getContacts() + externDataSource.getContacts()
    }

    override fun addContact(contact: Contact) {
        internDataSource.addContact(contact)
        externDataSource.addContact(contact)
    }

    override fun removeContact(contact: Contact) {
        internDataSource.removeContact(contact)
        externDataSource.removeContact(contact)
    }

}
