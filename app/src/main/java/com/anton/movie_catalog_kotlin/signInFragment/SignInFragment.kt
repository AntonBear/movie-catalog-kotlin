package com.anton.movie_catalog_kotlin.signInFragment

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.anton.movie_catalog_kotlin.R
import com.anton.movie_catalog_kotlin.databinding.SignInFragmentBinding
import kotlinx.coroutines.launch

class SignInFragment : Fragment(R.layout.sign_in_fragment) {

    private var _binding: SignInFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignInViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = SignInFragmentBinding.bind(view)

        with(binding) {
            signInButton.setOnClickListener {
                findNavController().navigate(R.id.action_signInFragment_to_mainHostFragment)
            }
            backStackButton.setOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
            loginEditText.doOnTextChanged { text, _, _, _ ->
                viewModel.onLoginTextChanged(text)
            }
            passwordEditText.doOnTextChanged { text, _, _, _ ->
                viewModel.onPasswordTextChanged(text)
            }
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    binding.signInButton.isEnabled = viewModel.isButtonEnable.value
                }
            }
        }




    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
