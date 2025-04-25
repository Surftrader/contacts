package com.example.contacts.screens.profile

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.contacts.R
import com.example.contacts.databinding.ActivityMyProfileBinding
import com.example.contacts.util.Navigator
import com.example.contacts.util.PreferencesManager
import com.example.contacts.util.loadImage

class MyProfileActivity : AppCompatActivity() {

    private val binding: ActivityMyProfileBinding by lazy {
        ActivityMyProfileBinding.inflate(
            layoutInflater
        )
    }

    private val preferencesManager: PreferencesManager by lazy {
        PreferencesManager(this)
    }

    private val navigator: Navigator by lazy {
        Navigator(this)
    }

    private val url = "https://poseal.com.ua/static/img/ava.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.my_profile)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initData()
        setListeners()
    }

    private fun initData() = with(binding) {
        textViewName.text = preferencesManager.getFullName()
        // Glide
        imgViewPhoto.loadImage(url)
        // Picasso
        //imgViewPhoto.loadImage(url, useGlide = false)
    }

    private fun setListeners() = with(binding) {
        btnViewLogout.setOnClickListener { logout() }
        btnContacts.setOnClickListener { showContacts() }
        btnEdit.setOnClickListener { showEditPage() }
    }

    private fun showEditPage() {
        navigator.navigateToEditProfile()
    }

    private fun showContacts() {
        navigator.navigateToContacts()
    }

    private fun logout() {
        preferencesManager.clearData()
        navigator.navigateToSignUp()
    }

    @Suppress("DEPRECATION")
    override fun finish() {
        super.finish()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            overrideActivityTransition(
                OVERRIDE_TRANSITION_OPEN, R.anim.slide_in_right, R.anim.slide_out_left
            )
        } else {
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }
}
