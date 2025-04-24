package com.example.contacts.screens.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.contacts.R
import com.example.contacts.databinding.ActivityEditProfileBinding
import com.example.contacts.util.AppConstants
import com.example.contacts.util.Navigator

class EditProfileActivity : AppCompatActivity() {

    private val binding: ActivityEditProfileBinding by lazy {
        ActivityEditProfileBinding.inflate(
            layoutInflater
        )
    }

    private val sharedPref: SharedPreferences by lazy {
        getSharedPreferences(AppConstants.STORE, Context.MODE_PRIVATE)
    }

    private val navigator: Navigator by lazy {
        Navigator(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.edit_profile)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setListeners()

    }

    private fun setListeners() = with(binding) {
        iconAddPhoto.setOnClickListener { changePhoto() }
        editArrowBack.setOnClickListener { backToProfile() }
    }

    private fun backToProfile() {
        navigator.navigateToMyProfile()
    }

    private fun changePhoto() {
        TODO("Open new fragment, Download photo")
    }
}