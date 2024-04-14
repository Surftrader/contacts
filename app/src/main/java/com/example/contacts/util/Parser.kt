package com.example.contacts.util

import java.util.Locale

private const val digitRegex: String = "[0-9]"

object Parser {
    fun getUsername(email: String): Pair<String, String> {
        val sanitizedEmail = email.replace(Regex(digitRegex), "")
        val atIndex = sanitizedEmail.indexOf('@')
        if (atIndex != -1) {
            val beforeAt = sanitizedEmail.substring(0, atIndex)
            val dotIndex = beforeAt.indexOf('.')
            if (dotIndex != -1) {
                val firstName = normalizeName(beforeAt.substring(0, dotIndex))
                val lastName = normalizeName(beforeAt.substring(dotIndex + 1))
                return Pair(firstName, lastName)
            }
        }
        return Pair("", "")
    }

    private fun normalizeName(name: String): String {
        return name
            .lowercase(Locale.ROOT)
            .replaceFirstChar {
                if (it.isLowerCase()) {
                    it.titlecase(Locale.ROOT)
                } else {
                    it.toString()
                }
            }
    }
}
