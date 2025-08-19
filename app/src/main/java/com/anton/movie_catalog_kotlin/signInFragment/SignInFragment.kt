package com.anton.movie_catalog_kotlin.signInFragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.anton.movie_catalog_kotlin.R
import com.anton.movie_catalog_kotlin.databinding.FragmentSignInBinding

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private var _binding: FragmentSignInBinding? = null
    val binding: FragmentSignInBinding = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSignInBinding.bind(view)
    }
}