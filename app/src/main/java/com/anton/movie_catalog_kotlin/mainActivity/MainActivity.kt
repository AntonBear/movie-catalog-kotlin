package com.anton.movie_catalog_kotlin.mainActivity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.anton.movie_catalog_kotlin.BuildConfig
import com.anton.movie_catalog_kotlin.R
import com.anton.movie_catalog_kotlin.databinding.MainActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Moviecatalogkotlin)
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        // Получаем NavController через NavHostFragment
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        // Авто-логин только для debug сборки
        if (BuildConfig.AUTO_LOGIN) {
            viewModel.loginUser(BuildConfig.LOGIN, BuildConfig.PASSWORD)
        }

        // После успешного логина переходим на MainHostFragment
        viewModel.loginResult.observe(this) { token ->
            if (token != null) {
                navController.navigate(R.id.mainHostFragment)
            }
        }
    }
}
