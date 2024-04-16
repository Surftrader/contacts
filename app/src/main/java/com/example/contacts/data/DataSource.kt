package com.example.contacts.data

interface DataSource<T> {
    fun getContacts(): List<T>

}
