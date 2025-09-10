package com.anton.movie_catalog_kotlin.movieDetailsFragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.anton.movie_catalog_kotlin.R
import com.anton.movie_catalog_kotlin.databinding.MoviesScreenFragmentBinding

class MovieDetailsScreenFragment : Fragment(R.layout.movies_screen_fragment) {

    private var _binding: MoviesScreenFragmentBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = MoviesScreenFragmentBinding.bind(view)
        Log.d("DEBUG", "App started - check if changes are applied")

    }

}