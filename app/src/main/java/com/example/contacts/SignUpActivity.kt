package com.example.contacts

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SignUpActivity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var hintValidEmail: TextView
    private lateinit var password: EditText
    private lateinit var hintValidPassword: TextView
    private lateinit var isRemembered: AppCompatCheckBox
    private lateinit var registerBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        email = findViewById(R.id.edit_text_email)
        hintValidEmail = findViewById(R.id.text_valid_email)
        password = findViewById(R.id.edit_text_password)
        hintValidPassword = findViewById(R.id.text_valid_password)
        isRemembered = findViewById(R.id.remember_check_box)
        registerBtn = findViewById(R.id.register_btn)

        password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                validatePassword(password, hintValidPassword)
            }
        })

        email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                validateEmail(email, hintValidEmail)
            }
        })

        registerBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            if (validateEmail(email, hintValidEmail) &&
                validatePassword(password, hintValidPassword)
            ) {
                startActivity(intent)
                finish()
            }
        }
    }

    private fun validateEmail(email: EditText, hintValidEmail: TextView): Boolean {
        val emailRegex = "^[A-Za-z0-9]+.[A-Za-z0-9]+@[A-Za-z0-9]+.[A-Za-z]+\$"
        return when {
            !email.text.toString().matches(emailRegex.toRegex()) -> {
                hintValidEmail.setText(R.string.incorrect_email)
                false
            }

            else -> {
                hintValidEmail.text = ""
                true
            }
        }
    }

    private fun validatePassword(password: EditText, hintValidPassword: TextView): Boolean {
        return when {
            password.text.toString().trim().isEmpty() ||
                    password.text.toString().trim().length < 8 ||
                    password.text.toString().trim().length > 20 -> {
                hintValidPassword.setText(R.string.incorrect_password)
                false
            }

            else -> {
                hintValidPassword.text = ""
                true
            }
        }
    }
}
