package com.example.contacts.screens.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.contacts.data.InternDataSource
import com.example.contacts.model.Contact

class ContactViewModel : ViewModel() {

    private val dataSource = InternDataSource()
    private val _contacts = MutableLiveData(dataSource.getContacts())
    val contacts: LiveData<List<Contact>> get() = _contacts

    fun removeContact(contact: Contact) {
        _contacts.value = _contacts.value?.filter { it.email != contact.email }
    }

    fun addContact(contact: Contact, position: Int) {
        _contacts.value = _contacts.value?.toMutableList()?.apply {
            if (position == -1) {
                add(contact)
            } else {
                add(position, contact)
            }
        }
    }
}
