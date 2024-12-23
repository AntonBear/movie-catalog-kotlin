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
import com.anton.movie_catalog_kotlin.R
import com.anton.movie_catalog_kotlin.databinding.FragmentFeedScreenBinding
import com.anton.movie_catalog_kotlin.movieDetailsScreen.MovieDetailsActivity
import com.google.android.material.chip.Chip

class FeedScreenFragment : Fragment() {
    private val viewModel: FeedScreenFragmentViewModel by viewModels()

    private var _binding: FragmentFeedScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.loadData()

        viewModel.country.observe(viewLifecycleOwner) { country ->
            viewModel.year.observe(viewLifecycleOwner) { year ->
                val countryYearText = SpannableString("$country • $year")
                binding.movieCountryYear.text = countryYearText
            }
        }

        viewModel.movieId.observe(viewLifecycleOwner) { movieId ->
            binding.moviePoster.setOnClickListener {
                movieId?.let {
                    val intent = Intent(requireContext(), MovieDetailsActivity::class.java)
                    intent.putExtra("id", it)
                    startActivity(intent)
                }
            }
        }

        val flexbox = binding.flexbox
        viewModel.genres.observe(viewLifecycleOwner) { genresList ->
            binding.flexbox.removeAllViews()
            genresList?.forEachIndexed { index, genre ->
                if (index < 3) {
                    val chip = Chip(requireContext(), null).apply {
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
                    flexbox.addView(chip)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}