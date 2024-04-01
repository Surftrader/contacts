package com.example.contacts

import android.content.Intent
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        with(binding) {
            registerBtn.setOnClickListener {
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
                    val username = Parser.getUsername(email)
                    val intent = Intent(this@SignUpActivity,
                        MyProfileActivity::class.java).also {
                            it.putExtra("firstName", username.first)
                            it.putExtra("lastName", username.second)
                        }
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }
}
