package com.anton.movie_catalog_kotlin.feedScreenFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.anton.movie_catalog_kotlin.R
import com.anton.movie_catalog_kotlin.databinding.FragmentFeedScreenBinding
import com.google.android.material.chip.Chip

class FeedScreenFragment : Fragment() {
    private val viewModel: FeedScreenFragmentViewModel by viewModels()
    private lateinit var binding: FragmentFeedScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedScreenBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.loadData()

        binding.moviePoster.setOnClickListener {
            findNavController().navigate(R.id.action_feed_screen_to_movie_details_screen)
        }

        viewModel.genres.observe(viewLifecycleOwner) { genresList ->
            binding.chipGroupLayout.removeAllViews()
            genresList?.forEach { genre ->
                val chip = Chip(requireContext()).apply {
                    text = genre
                }
                binding.chipGroupLayout.addView(chip)
            }
        }

        return binding.root
    }
}





