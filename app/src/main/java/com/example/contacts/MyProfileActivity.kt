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

class MyProfileActivity : AppCompatActivity() {

    private val binding: ActivityMyProfileBinding by lazy {
        ActivityMyProfileBinding.inflate(
            layoutInflater
        )
    }

    private var sharedPref: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.my_profile)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        sharedPref = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)

        binding.textViewName.text = initName()
        binding.btnViewLogout.setOnClickListener { logout() }
    }

    private fun initName(): String {
        return buildString {
            append(intent.getStringExtra("firstName"))
            append(" ")
            append(intent.getStringExtra("lastName"))
        }
    }

    private fun logout() {
        sharedPref?.edit()?.clear()?.apply()
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
                OVERRIDE_TRANSITION_OPEN, R.anim.slide_in_left, R.anim.slide_out_right
            )
        } else {
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
    }
}