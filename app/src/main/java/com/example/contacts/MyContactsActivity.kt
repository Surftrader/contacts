package com.example.contacts

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contacts.databinding.ActivityMyContactsBinding

class MyContactsActivity : AppCompatActivity() {

    private val binding: ActivityMyContactsBinding by lazy {
        ActivityMyContactsBinding.inflate(
            layoutInflater
        )
    }

    private val adapter = ContactAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.contacts_container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init()
    }

    private fun init() = with(binding){
        rcvContacts.layoutManager = LinearLayoutManager(this@MyContactsActivity)
        rcvContacts.adapter = adapter

        arrowBack.setOnClickListener { goBack() }
    }

    private fun goBack() {
        val intent = Intent(
            this@MyContactsActivity,
            MyProfileActivity::class.java
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
