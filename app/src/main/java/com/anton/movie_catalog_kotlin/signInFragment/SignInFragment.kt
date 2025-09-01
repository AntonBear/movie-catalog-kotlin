package com.anton.movie_catalog_kotlin.signInFragment

import android.os.Bundle
import android.util.Log
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.sign_in_fragment) {

    private var _binding: SignInFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignInViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = SignInFragmentBinding.bind(view)

        with(binding) {
            signInButton.setOnClickListener {
                viewModel.loginUser()
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
                    launch {
                        viewModel.userLogSuccess.collect { state ->
                            if (state) findNavController().navigate(R.id.action_signInFragment_to_mainHostFragment)
                            else {
                                showErrorDialog("Ошибка авторизации")
                            }
                        }
                    }
                    launch {
                        viewModel.isSignInButtonEnable.collect { isEnable ->
                            Log.d("debug", "isSignInButtonEnable ${isEnable}")
                            signInButton.isEnabled = isEnable
                        }
                    }
                    launch {
                        viewModel.loginErrorText.collect { error ->
                            loginTextInputLayout.error = error
                        }
                    }
                    launch {
                        viewModel.passwordErrorText.collect { error ->
                            passwordEditText.error = error
                        }
                    }
                }
            }
        }
    }


    private fun showErrorDialog(message: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(requireContext().getString(R.string.error_title))
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
