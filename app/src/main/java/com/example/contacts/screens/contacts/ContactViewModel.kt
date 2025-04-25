package com.example.contacts.screens.contacts

import androidx.lifecycle.ViewModel
import com.example.contacts.model.Contact
import com.example.contacts.repository.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val repository: ContactRepository
): ViewModel() {

    private val _contacts = MutableStateFlow<List<Contact>>(repository.getContacts())
    val contacts: StateFlow<List<Contact>> = _contacts

    fun removeContact(contact: Contact) {
        repository.removeContact(contact)
        _contacts.value = contacts.value.filter { it.email != contact.email }
    }

    fun addContact(contact: Contact, position: Int = -1) {
        repository.addContact(contact)
        val currentList = _contacts.value.toMutableList()
        if (position == -1 || position >= currentList.size) {
            currentList.add(contact)
        } else {
            currentList.add(position, contact)
        }
        _contacts.value = currentList
    }
}
