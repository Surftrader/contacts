package com.example.contacts.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PreferencesManager(context: Context) {

    private val sharedPref: SharedPreferences = context
        .getSharedPreferences(AppConstants.STORE, Context.MODE_PRIVATE)

    fun saveData(isRemember: Boolean, email: String, password: String) {
        val username = Parser.getUsername(email)
        sharedPref.edit {
            apply {
                putBoolean(IS_REMEMBER, isRemember)
                putString(EMAIL, email)
                putString(PASSWORD, password)
                putString(FULL_NAME, "${username.first} ${username.second}")
            }
        }
    }

    fun loadData(): Boolean {
        return sharedPref.getBoolean(IS_REMEMBER, false)
    }

    fun getEmail(): String? {
        return sharedPref.getString(EMAIL, null)
    }

    fun getFullName(): String {
        return sharedPref.getString(FULL_NAME, "").toString()
    }

    fun clearData() {
        sharedPref.edit{ clear() }
    }

    companion object {
        const val FULL_NAME = "fullName"
        const val EMAIL = "email"
        const val PASSWORD = "password"
        const val IS_REMEMBER = "isRemember"
    }
} 
