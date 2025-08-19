package com.anton.movie_catalog_kotlin.signInFragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.anton.movie_catalog_kotlin.R
import com.anton.movie_catalog_kotlin.databinding.SignInFragmentBinding

class SignInFragment : Fragment(R.layout.sign_in_fragment) {

    private var _binding: SignInFragmentBinding? = null
    val binding: SignInFragmentBinding get()  = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = SignInFragmentBinding.bind(view)

        with(binding) {
            signInButton.setOnClickListener {
                findNavController().navigate(R.id.action_signInFragment_to_mainHostFragment)
            }
        }
    }
}