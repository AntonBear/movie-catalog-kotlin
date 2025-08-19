package com.anton.movie_catalog_kotlin.mainActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anton.movie_catalog_kotlin.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}