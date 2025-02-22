package com.example.contacts.screens.profile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.contacts.R
import com.example.contacts.databinding.ActivityEditProfileBinding
import com.example.contacts.util.AppConstants

class EditProfileActivity : AppCompatActivity() {

    private val binding: ActivityEditProfileBinding by lazy {
        ActivityEditProfileBinding.inflate(
            layoutInflater
        )
    }

    private lateinit var sharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.edit_profile)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        sharedPref = getSharedPreferences(AppConstants.STORE, Context.MODE_PRIVATE)

        binding.iconAddPhoto.setOnClickListener { changePhoto() }
        binding.editArrowBack.setOnClickListener { backToProfile() }

    }

    private fun backToProfile() {
        val intent = Intent(
            this@EditProfileActivity,
            MyProfileActivity::class.java
        )
        startActivity(intent)
        finish()
    }

    private fun changePhoto() {
        TODO("Open new fragment, Download photo")
    }
}