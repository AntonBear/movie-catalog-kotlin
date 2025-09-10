package com.anton.movie_catalog_kotlin.feedScreenFragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.anton.movie_catalog_kotlin.R
import com.anton.movie_catalog_kotlin.databinding.FeedScreenFragmentBinding
import com.anton.movie_catalog_kotlin.models_old.Genre
import com.anton.movie_catalog_kotlin.models_old.GenreModel
import com.anton.movie_catalog_kotlin.movieDetailsScreen.MovieDetailsActivity
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
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

        // Подписка на Flow
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movieData.collectLatest { movie ->
                    movie?.let {
                        binding.movieTitle.text = it.name
                        binding.movieCountryYear.text = "${it.country} • ${it.year}"

                        Glide.with(binding.root.context)
                            .load(it.poster)
                            .error(R.drawable.error_image)
                            .into(binding.moviePoster)

                        // Жанры
                        binding.genreChipGroup.removeAllViews()
                        setChipsSingleLine(binding.genreChipGroup,movie.genres)
//                        it.genres.forEach { genre ->
//                            val chip = Chip(binding.root.context).apply {
//                                text = genre.name
//                                isCheckable = false
//                            }
//                            binding.genreChipGroup.addView(chip)
//                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        viewModel.loadData() // триггерим обновление при возврате
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    fun setChipsSingleLine(chipGroup: ChipGroup, genres: List<GenreModel>) {
        chipGroup.removeAllViews()

        chipGroup.post { // ждем, пока layout измерит ширину
            val maxWidth = chipGroup.width
            var usedWidth = 0

            for (genre in genres) {
                val chip = Chip(chipGroup.context).apply { text = genre.name }

                chip.measure(
                    View.MeasureSpec.UNSPECIFIED,
                    View.MeasureSpec.UNSPECIFIED
                )
                val chipWidth = chip.measuredWidth

                if (usedWidth + chipWidth > maxWidth) break

                usedWidth += chipWidth
                chipGroup.addView(chip)
            }
        }
    }


}
