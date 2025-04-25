package com.example.contacts.model

import androidx.annotation.DrawableRes
import com.example.contacts.R

data class Contact (
    @DrawableRes
    val imageId: Int = R.drawable.ic_person,
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
