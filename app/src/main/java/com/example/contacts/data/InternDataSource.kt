package com.example.contacts.data

import com.example.contacts.R
import com.example.contacts.model.Contact

class InternDataSource : DataSource<Contact> {
    override fun getContacts(): List<Contact> {
        return listOf(
            Contact(
                imageId = R.drawable.ava_smith,
                email = "ava.smith@gmail.com",
                firstName = "Ava",
                lastName = "Smith",
                profession = "Photograph"
            ),
            Contact(
                imageId = R.drawable.jessie_brown,
                email = "jessie.brown@gmail.com",
                firstName = "Jessie",
                lastName = "Brown",
                profession = "Actress"
            ),
            Contact(
                imageId = R.drawable.jackie_taylor,
                email = "jackie.taylor@gmail.com",
                firstName = "Jackie",
                lastName = "Taylor",
                profession = "Financier"
            ),
            Contact(
                imageId = R.drawable.jenny_walker,
                email = "jenny.walker@gmail.com",
                firstName = "Jenny",
                lastName = "Walker",
                profession = "Make-up artist"
            ),
            Contact(
                imageId = R.drawable.freddy_harris,
                email = "freddy.harris@gmail.com",
                firstName = "Freddy",
                lastName = "Harris",
                profession = "Secretary"
            )
        )
    }
}
