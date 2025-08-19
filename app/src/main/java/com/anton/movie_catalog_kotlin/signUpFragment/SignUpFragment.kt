package com.anton.movie_catalog_kotlin.signUpFragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.anton.movie_catalog_kotlin.R
import com.anton.movie_catalog_kotlin.databinding.SignUpFragmentBinding

class SignUpFragment : Fragment(R.layout.sign_up_fragment) {

    private var _binding: SignUpFragmentBinding? = null
    val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = SignUpFragmentBinding.bind(view)

        with(binding) {
            signUpButton.setOnClickListener {
                findNavController().navigate(R.id.action_signUpFragment_to_mainHostFragment)
            }
        }
    }
}