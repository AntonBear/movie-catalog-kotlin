package com.anton.movie_catalog_kotlin.signUpFragment

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
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
import kotlinx.coroutines.launch
import java.util.Locale

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
            loginEditText.doOnTextChanged {
                viewModel.
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {


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