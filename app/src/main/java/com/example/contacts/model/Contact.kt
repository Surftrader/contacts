package com.example.contacts.model

import androidx.annotation.DrawableRes

data class Contact (
    @DrawableRes
    val imageId: Int = -1,
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val career: String = "",
    val address: String = "",
    val birthday: String = "",
    val password: String = "",
    val profession: String = "",
    val mobile: String = ""
)
