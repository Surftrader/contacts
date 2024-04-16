package com.example.contacts.data

import com.example.contacts.model.Contact

class ExternalDataSource : DataSource<Contact> {
    override fun getContacts(): List<Contact> {
        return emptyList()
    }
}
