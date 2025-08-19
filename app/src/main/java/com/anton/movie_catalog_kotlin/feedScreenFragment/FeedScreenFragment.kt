package com.anton.movie_catalog_kotlin.feedScreenFragment

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.anton.movie_catalog_kotlin.R
import com.anton.movie_catalog_kotlin.databinding.FeedScreenFragmentBinding
import com.anton.movie_catalog_kotlin.movieDetailsScreen.MovieDetailsActivity
import com.google.android.material.chip.Chip

class FeedScreenFragment : Fragment() {
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

        viewModel.loadData()

        viewModel.movieData.observe(viewLifecycleOwner) { movieDetails ->
            movieDetails?.let {
                binding.movieTitle.text = it.name
                val countryYearText = SpannableString("${it.country.split(",").firstOrNull()?.trim()} • ${it.year}")
                binding.movieCountryYear.text = countryYearText
                binding.moviePoster.load(it.poster)

                updateChips(it.genres)

                binding.moviePoster.setOnClickListener { _ ->
                    val intent = Intent(requireContext(), MovieDetailsActivity::class.java)
                    intent.putExtra("id", it.id)
//                    intent.putExtra("id", "b6c5228b-91fb-43a1-a2ac-08d9b9f3d2a2")
                    startActivity(intent)
                }
            }
        }
    }

    private fun updateChips(genres: List<String>?) {
        binding.flexbox.removeAllViews()
        genres?.take(3)?.forEach { genre ->
            val chip = Chip(requireContext()).apply {
                text = genre
                shapeAppearanceModel = shapeAppearanceModel.toBuilder()
                    .setAllCornerSizes(resources.getDimension(R.dimen.chip_corner_radius))
                    .build()
                setChipBackgroundColorResource(R.color.dark_faded)
                setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

                val layoutParams = ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    marginStart = resources.getDimensionPixelSize(R.dimen.chip_margin)
                    marginEnd = resources.getDimensionPixelSize(R.dimen.chip_margin)
                }
                this.layoutParams = layoutParams
            }
            binding.flexbox.addView(chip)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}