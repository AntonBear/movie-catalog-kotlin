package com.anton.movie_catalog_kotlin.welcomeScreenFragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.anton.movie_catalog_kotlin.R
import com.anton.movie_catalog_kotlin.databinding.WelcomeScreenFragmentBinding

class WelcomeScreenFragment() : Fragment(R.layout.welcome_screen_fragment) {

    private var _binding: WelcomeScreenFragmentBinding? = null
    val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = WelcomeScreenFragmentBinding.bind(view)

        with(binding) {
            buttonEnterAccount.setOnClickListener {
                findNavController().navigate(R.id.action_welcome_screen_fragment_to_signInFragment)
            }
            buttonSignUpAccount.setOnClickListener {
                findNavController().navigate(R.id.action_welcome_screen_fragment_to_signUpFragment)
            }
        }
    }
}