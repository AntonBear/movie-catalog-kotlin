package com.anton.movie_catalog_kotlin.profileScreenFragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.anton.movie_catalog_kotlin.R
import com.anton.movie_catalog_kotlin.databinding.ProfileScreenFragmentBinding

class ProfileScreenFragment() : Fragment(R.layout.profile_screen_fragment) {
    private var _binding: ProfileScreenFragmentBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = ProfileScreenFragmentBinding.bind(view)
    }

}