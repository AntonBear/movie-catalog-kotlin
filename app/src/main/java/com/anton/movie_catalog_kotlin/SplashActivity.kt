package com.anton.movie_catalog_kotlin

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.anton.movie_catalog_kotlin.databinding.ActivityLaunchScreenBinding

class SplashActivity : AppCompatActivity() {
    private val splashViewModel: SplashViewModel by viewModels()
    private lateinit var binding: ActivityLaunchScreenBinding

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == "ACTION_MAIN_ACTIVITY_LOADED") {
                finish()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intentFilter = IntentFilter("ACTION_MAIN_ACTIVITY_LOADED")
        registerReceiver(broadcastReceiver, intentFilter, RECEIVER_NOT_EXPORTED)

        splashViewModel.isLoading.observe(this, Observer { isLoading ->
            if (!isLoading) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })

        splashViewModel.startLoading()
    }

    override fun onDestroy() {
        unregisterReceiver(broadcastReceiver)
        super.onDestroy()
    }

}