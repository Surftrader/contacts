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
import com.example.contacts.databinding.ActivitySignUpBinding
import com.example.contacts.util.Parser
import com.example.contacts.util.Validator

class SignUpActivity : AppCompatActivity() {

    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(
            layoutInflater
        )
    }

    private var sharedPref: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.sing_up)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        sharedPref = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)

        loadData()

        binding.registerBtn.setOnClickListener { switchVisibility() }
    }

    private fun switchVisibility() = with(binding) {

        val email = editTextEmail.text.toString().trim()
        val password = editTextPassword.text.toString().trim()
        val validEmail = Validator.isValidEmail(email)
        val validPassword = Validator.isValidPassword(password)

        textViewEmail.helperText = if (!validEmail) {
            getString(R.string.incorrect_email)
        } else {
            null
        }

        textViewPassword.helperText = if (!validPassword) {
            getString(R.string.incorrect_password)
        } else {
            null
        }

        if (validEmail && validPassword) {
            saveData(rememberCheckBox.isChecked, email, password)
            moveToMyProfile(email)
        }
    }

    private fun moveToMyProfile(email: String) {
        val username = Parser.getUsername(email)
        val intent = Intent(
            this@SignUpActivity,
            MyProfileActivity::class.java
        ).also {
            it.putExtra("firstName", username.first)
            it.putExtra("lastName", username.second)
        }
        startActivity(intent)
        finish()
    }

    private fun saveData(isRemember: Boolean, email: String, password: String) {
        sharedPref?.edit()!!
            .apply {
                putBoolean("isRemember", isRemember)
                putString("email", email)
                putString("password", password)
            }.apply()
    }

    private fun loadData() {
        if (sharedPref?.getBoolean("isRemember", false)!!) {
            moveToMyProfile(sharedPref?.getString("email", "")!!)
        }
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
