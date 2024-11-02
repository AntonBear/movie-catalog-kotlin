package com.anton.movie_catalog_kotlin.signup

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.anton.movie_catalog_kotlin.databinding.ActivitySingUpBinding
import com.anton.movie_catalog_kotlin.models.Gender
import com.anton.movie_catalog_kotlin.signin.SignInActivity
import java.util.Locale


class SignUpActivity: AppCompatActivity() {
    private lateinit var binding: ActivitySingUpBinding
    private lateinit var dateOfBirthEditText: EditText
    private val viewModel: SingUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dateOfBirthEditText = binding.dateOfBirthEditText

        binding.loginEditText.setText("Anton2")
        binding.dateOfBirthEditText.setText("2024-11-06T10:26:03.128Z")
        binding.emailEditText.setText("holzed15@gmail.com")
        binding.nameEditText.setText("Anton")
        binding.passwordEditText.setText("greedisgood")
        binding.passwordConfirmEditText.setText("greedisgood")

        binding.squareButton.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
        dateOfBirthEditText.setOnClickListener {
            showDatePickerDialog()
        }
        binding.maleButton.setOnClickListener {
            viewModel.selectedGender = Gender.MALE
        }
        binding.femaleButton.setOnClickListener {
            viewModel.selectedGender = Gender.FEMALE
        }


        binding.singUpButton.setOnClickListener {
            val context = this
            with(binding) {
                if (validateInputFields(listOf(loginEditText, emailEditText, nameEditText, passwordEditText, dateOfBirthEditText))) {
                    val userName = binding.loginEditText.text.toString()
                    val passWord = binding.passwordEditText.text.toString()
                    val name = binding.nameEditText.text.toString()
                    val email = binding.emailEditText.text.toString()
                    val birthDate = binding.dateOfBirthEditText.text.toString()
                    val passwordConfirmEditText = binding.passwordConfirmEditText.text.toString()
                    val selectedGender = viewModel.selectedGender
                    if (selectedGender != null) {
                        viewModel.singUp(userName,name,passWord,passwordConfirmEditText, email,birthDate, selectedGender, context)
                    }
                } else {
                    println("Введите все поля регистрации")
                }
            }
        }
}
    private fun validateInputFields(editTextList: List<EditText>): Boolean {
        return editTextList.all { it.text.isNotEmpty() }
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
                dateOfBirthEditText.setText(formattedDate)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }


}