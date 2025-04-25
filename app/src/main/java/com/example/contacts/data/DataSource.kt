package com.example.contacts.data
import com.example.contacts.model.Contact

interface DataSource {
    fun getContacts(): List<Contact>
}
