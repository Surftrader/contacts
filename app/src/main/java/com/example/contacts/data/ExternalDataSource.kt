package com.example.contacts.data

import com.example.contacts.model.User

class ExternalDataSource : DataSource<User> {
    override fun getContacts(): ArrayList<User> {
        return arrayListOf()
    }
}
