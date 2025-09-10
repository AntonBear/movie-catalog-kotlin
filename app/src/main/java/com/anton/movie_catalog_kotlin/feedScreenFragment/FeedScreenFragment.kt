package com.anton.movie_catalog_kotlin.feedScreenFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.anton.movie_catalog_kotlin.R
import com.anton.movie_catalog_kotlin.databinding.FeedScreenFragmentBinding
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FeedScreenFragment : Fragment(R.layout.feed_screen_fragment) {

    private val viewModel: FeedScreenFragmentViewModel by viewModels()
    private var _binding: FeedScreenFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FeedScreenFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.movieData.collectLatest { movie ->
                        movie?.let {
                            binding.movieTitle.text = it.name
                            binding.movieCountryYear.text = "${it.country} • ${it.year}"

                            Glide.with(binding.root.context)
                                .load(it.poster)
                                .error(R.drawable.error_image)
                                .into(binding.moviePoster)

                            binding.genreChipGroup.removeAllViews()

                            it.genres.take(3).forEach { genre ->
                                val chip = Chip(binding.root.context).apply {
                                    text = genre.name
                                    isCheckable = false
                                }
                                binding.genreChipGroup.addView(chip)
                            }
                        }
                    }

                }
                launch {
                    viewModel.navigateToMovieDetails.collect {
//                        findNavController().navigate()
                    }
                }

            }
        }
    }

    override fun onResume() {
        viewModel.loadData()
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
