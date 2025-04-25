package com.example.contacts.util

import android.content.Context
import android.content.Intent
import com.example.contacts.screens.auth.SignUpActivity
import com.example.contacts.screens.contacts.MyContactsActivity
import com.example.contacts.screens.profile.EditProfileActivity
import com.example.contacts.screens.profile.MyProfileActivity

class Navigator(private val context: Context) {

    sealed class Destination {
        object EditProfile : Destination()
        object Contacts : Destination()
        object SignUp : Destination()
        object MyProfile : Destination()
    }

    private fun createIntent(destination: Destination): Intent {
        return when (destination) {
            is Destination.EditProfile -> Intent(context, EditProfileActivity::class.java)
            is Destination.Contacts -> Intent(context, MyContactsActivity::class.java)
            is Destination.SignUp -> Intent(context, SignUpActivity::class.java)
            is Destination.MyProfile -> Intent(context, MyProfileActivity::class.java)
        }
    }

    fun navigateTo(destination: Destination) {
        val intent = createIntent(destination)
        context.startActivity(intent)
    }

    fun navigateToEditProfile() = navigateTo(Destination.EditProfile)
    fun navigateToContacts() = navigateTo(Destination.Contacts)
    fun navigateToSignUp() = navigateTo(Destination.SignUp)
    fun navigateToMyProfile() = navigateTo(Destination.MyProfile)
}
