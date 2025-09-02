package com.anton.movie_catalog_kotlin.signUpFragment

import android.app.DatePickerDialog
import android.icu.util.Calendar
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
import com.anton.movie_catalog_kotlin.databinding.SignUpFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
    class SignUpFragment : Fragment(R.layout.sign_up_fragment) {

        private var _binding: SignUpFragmentBinding? = null
        val binding get() = _binding!!
        private val viewModel: SignUpViewModel by viewModels()

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            _binding = SignUpFragmentBinding.bind(view)

            with(binding) {
                signUpButton.setOnClickListener {
                    findNavController().navigate(R.id.action_signUpFragment_to_mainHostFragment)
                }
                backStackButton.setOnClickListener {
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }
                maleButton.setOnClickListener {
                    viewModel.onMaleGenderChanged()
                }
                femaleButton.setOnClickListener {
                    viewModel.onFemaleGenderChanged()
                }
                signUpButton.setOnClickListener {}

                loginEditText.doOnTextChanged { text, _, _, _ ->
                    viewModel.onUserLoginInputChanged(text)
                }
                loginEditText.setOnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus) {
                        viewModel.onUserLoginFocusLost()
                    }
                }
                emailEditText.doOnTextChanged { text, _, _, _ ->
                    viewModel.updateEmailText(text)

                }
                emailEditText.setOnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus) {
                        viewModel.emailUserOnFocusValid(emailEditText.text)
                    }
                }
                userNameEditText.doOnTextChanged { text, _, _, _ ->
                    viewModel.onUserNameInputChanged(text)
                }
                userNameEditText.setOnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus) {
                        viewModel.onUserNameFocusLost()
                    }
                }
                passwordEditText.doOnTextChanged { text, _, _, _ ->
                    viewModel.onPasswordInputChanged(text)
                    viewModel.checkPasswordsMatch()
                }

                passwordEditText.setOnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus) {
                        viewModel.onPasswordFocusLost()
                    }
                }

                passwordConfirmEditText.doOnTextChanged { text, _, _, _ ->
                    viewModel.onConfirmPasswordTextChanged(text)
                    viewModel.checkPasswordsMatch()
                }
                passwordConfirmEditText.setOnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus) {
                        viewModel.onConfirmPasswordFocusLost()
                    }
                }

                dateOfBirthEditText.setOnClickListener {
                    showDatePickerDialog()
                }

                viewLifecycleOwner.lifecycleScope.launch {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        launch {
                            viewModel.isMaleGenderSelected.collect { isSelected ->
                                maleButton.isSelected = isSelected
                            }
                        }
                        launch {
                            viewModel.isFemaleGenderSelected.collect { isSelected ->
                                femaleButton.isSelected = isSelected
                            }
                        }
                        launch {
                            viewModel.isSignUpButtonEnabled.collect { isEnabled ->
                                signUpButton.isEnabled = isEnabled
                            }
                        }
                        launch {
                            viewModel.birthDate.collect { date ->
                                dateOfBirthEditText.setText(date)
                            }
                        }
                        launch {
                            viewModel.errorLogin.collect { error ->
                                loginTextInput.error = error
                            }
                        }
                        launch {
                            viewModel.emailError.collect { error ->
                                emailTextInput.error = error
                            }
                        }
                        launch {
                            viewModel.errorUserName.collect { error ->
                                userNameTextInput.error = error
                            }
                        }
                        launch {
                            viewModel.passwordError.collect { error ->
                                passwordTextInput.error = error
                            }
                        }
                        launch {
                            viewModel.confirmPasswordError.collect { error ->
                                passwordConfirmTextInput.error = error
                            }
                        }
                        launch {
                            viewModel.birthdayDateError.collect { error ->
                                dateOfBirthTextInput.error = error
                            }
                        }
                    }
                }

            }
        }

        private fun showDatePickerDialog() {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                requireActivity(),
                { _, year, monthOfYear, dayOfMonth ->
                    viewModel.onBirthDateInputChanged(year, monthOfYear, dayOfMonth)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }


    }