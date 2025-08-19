package com.anton.movie_catalog_kotlin.mainHostFragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.anton.movie_catalog_kotlin.databinding.MainHostFragmentBinding

class MainHostFragment : Fragment() {

    private var _binding: MainHostFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = MainHostFragmentBinding.bind(view)
    }

    // TODO Токен действителен?
}