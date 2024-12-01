package com.anton.movie_catalog_kotlin.signup

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.anton.movie_catalog_kotlin.MainActivity
import com.anton.movie_catalog_kotlin.R
import com.anton.movie_catalog_kotlin.databinding.ActivitySignUpBinding
import com.anton.movie_catalog_kotlin.signin.SignInActivity
import com.anton.movie_catalog_kotlin.utils.showErrorDialog
import com.anton.movie_catalog_kotlin.utils.showSuccessDialog
import kotlinx.coroutines.launch
import java.util.Locale


class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: SignUpViewModel by viewModels { SignUpViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)

        binding.squareButton.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
        binding.dateOfBirthEditText.setOnClickListener { showDatePickerDialog() }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.progressBar.visibility =
                        if (state.isLoading) View.VISIBLE else View.GONE
                    binding.signUpButton.text =
                        if (state.isLoading) getString(R.string.sign_up_progress)
                        else getString(R.string.sign_up)
                    binding.signUpButton.isEnabled = state.isSignUpButtonEnabled
                    binding.femaleButton.isSelected = state.isFemaleSelected
                    binding.maleButton.isSelected = state.isMaleSelected
                    when (state.error) {
                        is SignUpError.InvalidFields -> showErrorDialog(
                            R.string.error_title,
                            getString(R.string.error_valid)
                        )
                        is SignUpError.RequestError -> showErrorDialog(
                            R.string.error_title,
                            state.error.message ?: getString(R.string.error_default)
                        )
                        else -> Unit
                    }

                    if (state.isUserLoggedIn) {
                        val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                        showSuccessDialog(R.string.success_title, R.string.signup_success_notion) {
                            startActivity(
                                intent
                            )
                        }
                    }

                    viewModel.onHandleError()
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
                viewModel.onDateSelected(year, monthOfYear, dayOfMonth)
                binding.dateOfBirthEditText.setText(formattedDate)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }
}
