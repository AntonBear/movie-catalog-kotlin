package com.anton.movie_catalog_kotlin.moviesScreenFragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.anton.movie_catalog_kotlin.R
import com.anton.movie_catalog_kotlin.databinding.MoviesScreenFragmentBinding

class MoviesScreenFragment : Fragment(R.layout.movies_screen_fragment) {

    private var _binding: MoviesScreenFragmentBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = MoviesScreenFragmentBinding.bind(view)
    }

}