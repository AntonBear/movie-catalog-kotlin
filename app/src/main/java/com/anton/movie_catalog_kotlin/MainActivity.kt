package com.anton.movie_catalog_kotlin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anton.movie_catalog_kotlin.databinding.ActivityWelcomeScreenBinding
import com.anton.movie_catalog_kotlin.signin.SignInActivity
import com.anton.movie_catalog_kotlin.signup.SignUpActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonEnterAccount.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
        binding.buttonSignUpAccount.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        val intent = Intent("ACTION_MAIN_ACTIVITY_LOADED")
        sendBroadcast(intent)

    }
}