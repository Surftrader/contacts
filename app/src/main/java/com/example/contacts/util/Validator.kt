package com.example.contacts.util

private const val emailRegex: String = "^[A-Za-z0-9]+.[A-Za-z0-9]+@[A-Za-z0-9.-]+\$"
private const val emailMaxSize: Int = 50
private const val passwordMaxSize: Int = 20
private const val passwordMinSize: Int = 8

object Validator {
    fun isValidEmail(email: String): Boolean {
        return !(!email.matches(emailRegex.toRegex()) || email.length > emailMaxSize)
    }

    fun isValidPassword(password: String): Boolean {
        return !(password.length < passwordMinSize || password.length > passwordMaxSize)
    }
}
