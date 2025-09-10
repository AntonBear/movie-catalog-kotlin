package com.anton.movie_catalog_kotlin.movieDetailsFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.anton.movie_catalog_kotlin.R
import com.anton.movie_catalog_kotlin.databinding.MoviesScreenFragmentBinding
import com.anton.movie_catalog_kotlin.movieDetailsScreen.MovieDetailsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {}
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("DEBUG", "App started - check if changes are applied")

    }

}