package com.example.contacts

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.contacts.databinding.ActivityMyProfileBinding
import com.example.contacts.util.AppConstants
import com.example.contacts.util.GlideDownloader
import com.example.contacts.util.ImageDownloader
import com.example.contacts.util.PicassoDownloader

class MyProfileActivity : AppCompatActivity() {

    private val binding: ActivityMyProfileBinding by lazy {
        ActivityMyProfileBinding.inflate(
            layoutInflater
        )
    }

    private lateinit var sharedPref: SharedPreferences

    private val url = "https://poseal.com.ua/static/img/ava.jpg"

    private lateinit var imageDownloader: ImageDownloader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.my_profile)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        sharedPref = getSharedPreferences(AppConstants.STORE, Context.MODE_PRIVATE)

        with(binding) {
            textViewName.text = initName()
            btnViewLogout.setOnClickListener { logout() }
            btnContacts.setOnClickListener { showContacts() }
            btnEdit.setOnClickListener { showEditPage() }
        }
        // Glide
        imageDownloader = GlideDownloader(this)
        // Picasso
        // imageDownloader = PicassoDownloader(url, binding.imgViewPhoto)

        imageDownloader.downloadImage(url, binding.imgViewPhoto)

    }

    private fun showEditPage() {
        val intent = Intent(
            this@MyProfileActivity,
            EditProfileActivity::class.java
        )
        startActivity(intent)
        finish()
    }

    private fun showContacts() {
        val intent = Intent(
            this@MyProfileActivity,
            MyContactsActivity::class.java
        )
        startActivity(intent)
        finish()
    }

    private fun initName(): String {
        return sharedPref.getString(FULL_NAME, "").toString()
    }

    private fun logout() {
        sharedPref.edit()?.clear()?.apply()
        val intent = Intent(
            this@MyProfileActivity,
            SignUpActivity::class.java
        )
        startActivity(intent)
        finish()
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
