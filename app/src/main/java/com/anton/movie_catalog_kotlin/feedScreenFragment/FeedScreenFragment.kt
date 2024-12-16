package com.anton.movie_catalog_kotlin.feedScreenFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.anton.movie_catalog_kotlin.databinding.FragmentFeedScreenBinding
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch


class FeedScreenFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private val viewModel: FeedScreenFragmentViewModel by viewModels()
    private lateinit var binding: FragmentFeedScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedScreenBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.loadData()

        viewModel.genres.observe(viewLifecycleOwner) { genresList ->
            binding.chipGroupLayout.removeAllViews()
            genresList?.forEach { genre ->
                genre?.let {
                    viewLifecycleOwner.lifecycleScope.launch {
                        val chip = Chip(requireContext())
                        chip.text = genre
                        binding.chipGroupLayout.addView(chip)
                    }
                }
            }
        }
        return binding.root

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BlankFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FeedScreenFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}





