package com.example.contacts.data

import com.example.contacts.R
import com.example.contacts.model.Contact

public class InternalDataSource : DataSource<Contact> {
    override fun getContacts(): List<Contact> {
        return List(10) {
            Contact(
                imageId = R.drawable.ava_smith,
                email = "$it",
                firstName = "Ava $it",
                lastName = "Smith $it",
                password = "$it",
                profession = "Photograph $it",
                mobile = "$it"
            )
        }
    }
}
