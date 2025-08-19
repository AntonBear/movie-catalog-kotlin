package com.anton.movie_catalog_kotlin.splashScreenFragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.anton.movie_catalog_kotlin.R
import com.anton.movie_catalog_kotlin.databinding.SplashScreenFragmentBinding

class SplashScreenFragment : Fragment(R.layout.splash_screen_fragment) {

    private var _binding: SplashScreenFragmentBinding? = null
    val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = SplashScreenFragmentBinding.bind(view)
    }
}