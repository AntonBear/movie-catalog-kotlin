package com.anton.movie_catalog_kotlin.signin

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.anton.movie_catalog_kotlin.MainActivity
import com.anton.movie_catalog_kotlin.databinding.ActivitySingInBinding

class SignInActivity: AppCompatActivity() {

    private lateinit var binding: ActivitySingInBinding
    private val viewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.squareButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.loginEditText.setText("Anton")
        binding.passwordEditText.setText("greedisgood")

        binding.singInButton.setOnClickListener {
            val username = binding.loginEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            viewModel.login(username, password)


        }
    }

}