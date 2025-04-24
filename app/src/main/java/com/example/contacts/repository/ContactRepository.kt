package com.example.contacts.repository

import com.example.contacts.model.Contact

interface ContactRepository {
    fun getContacts(): List<Contact>
    fun addContact(contact: Contact)
    fun removeContact(contact: Contact)
}
