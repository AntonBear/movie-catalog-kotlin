package com.anton.movie_catalog_kotlin.signup

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.anton.movie_catalog_kotlin.R
import com.anton.movie_catalog_kotlin.databinding.ActivitySingUpBinding
import com.anton.movie_catalog_kotlin.models.Gender
import com.anton.movie_catalog_kotlin.signin.SignInActivity
import com.anton.movie_catalog_kotlin.utils.showErrorDialog
import com.anton.movie_catalog_kotlin.utils.showSuccessDialog
import kotlinx.coroutines.launch
import java.util.Locale


class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySingUpBinding
    private val viewModel: SignUpViewModel by viewModels { SignUpViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.squareButton.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
        binding.dateOfBirthEditText.setOnClickListener { showDatePickerDialog() }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.selectedGender.collect { gender ->
                    binding.maleButton.isSelected = gender == Gender.MALE
                    binding.femaleButton.isSelected = gender == Gender.FEMALE
                }
            }
        }
        binding.maleButton.setOnClickListener { viewModel.selectGender(Gender.MALE) }
        binding.femaleButton.setOnClickListener { viewModel.selectGender(Gender.FEMALE) }
        binding.singUpButton.setOnClickListener {
            viewModel.signUp(
                binding.loginEditText.text.toString(),
                binding.nameEditText.text.toString(),
                binding.passwordEditText.text.toString(),
                binding.passwordConfirmEditText.text.toString(),
                binding.emailEditText.text.toString(),
                binding.dateOfBirthEditText.text.toString(),
            )
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.signUpState.collect { state ->
                    when (state) {
                        is SignUpViewModel.SignUpState.Loading -> binding.progressBar.show()
                        is SignUpViewModel.SignUpState.Success -> {
                            binding.progressBar.hide()
                            showSuccessDialog(R.string.success_title, R.string.signup_success_notion) {
                                startActivity(Intent(this@SignUpActivity, SignInActivity::class.java))
                                finish()
                            }
                        }
                        is SignUpViewModel.SignUpState.Error -> {
                            binding.progressBar.hide()
                            showErrorDialog(R.string.error_title, state.message.toIntOrNull()?:R.string.error_title)
                        }
                        is SignUpViewModel.SignUpState.InputFieldsInvalid -> {
                            showErrorDialog(R.string.error_title, R.string.error_fill_all_fields)
                        }
                        else -> binding.progressBar.hide()
                    }
                }
            }
        }
    }
    private fun ProgressBar.show() {
        this.visibility = View.VISIBLE
    }
    private fun ProgressBar.hide() {
        this.visibility = View.GONE
    }
    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, monthOfYear, dayOfMonth ->
                val locale = Locale("ru", "RU")
                val formattedDate =
                    SimpleDateFormat("dd MMMM yyyy", locale)
                        .format(Calendar.getInstance().apply {
                            set(Calendar.YEAR, year)
                            set(Calendar.MONTH, monthOfYear)
                            set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        }.time)
                binding.dateOfBirthEditText.setText(formattedDate)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }
}