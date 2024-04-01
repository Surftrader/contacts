package com.example.contacts.util

import java.util.Locale

private const val digitRegex: String = "[0-9]"
object Parser {
    fun getUsername(email: String): Pair<String, String> {
        val sanitizedEmail = email.replace(Regex(digitRegex),"")
        val atIndex = sanitizedEmail.indexOf('@')
        if (atIndex != -1) {
            val beforeAt = sanitizedEmail.substring(0, atIndex)
            val dotIndex = beforeAt.indexOf('.')
            if (dotIndex != -1) {
                val firstName = beforeAt.substring(0, dotIndex)
                    .lowercase(Locale.ROOT)
                    .replaceFirstChar {
                        if (it.isLowerCase()) {
                            it.titlecase(Locale.ROOT)
                        } else {
                            it.toString()
                        }
                    }
                val lastName = beforeAt.substring(dotIndex + 1)
                    .lowercase(Locale.ROOT)
                    .replaceFirstChar {
                        if (it.isLowerCase()) {
                            it.titlecase(Locale.ROOT)
                        } else {
                            it.toString()
                        }
                    }
                return Pair(firstName, lastName)
            }
        }
        return Pair("", "")
    }
}
