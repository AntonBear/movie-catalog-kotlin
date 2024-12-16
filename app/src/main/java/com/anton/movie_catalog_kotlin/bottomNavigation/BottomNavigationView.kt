package com.anton.movie_catalog_kotlin.bottomNavigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.anton.movie_catalog_kotlin.R
import com.anton.movie_catalog_kotlin.databinding.ActivityBottomNavigationBinding

class BottomNavigationView : AppCompatActivity() {
    private lateinit var binding: ActivityBottomNavigationBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}