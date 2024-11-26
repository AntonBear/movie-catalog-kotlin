package com.anton.movie_catalog_kotlin.signin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.anton.movie_catalog_kotlin.MainActivity
import com.anton.movie_catalog_kotlin.R
import com.anton.movie_catalog_kotlin.databinding.ActivitySingInBinding
import com.anton.movie_catalog_kotlin.navigationBar.NavigationBarView
import com.anton.movie_catalog_kotlin.utils.DialogUtils.showErrorDialog
import com.anton.movie_catalog_kotlin.utils.DialogUtils.showSuccessDialog
import com.anton.movie_catalog_kotlin.utils.Result
import kotlinx.coroutines.launch

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySingInBinding
    private val viewModel: SignInViewModel by viewModels { SignInViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.squareButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.singInButton.setOnClickListener {
            val username = binding.loginEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            lifecycleScope.launch {
                viewModel.login(username, password)
            }
        }

        viewModel.loginResult.observe(this) { result ->
            if (!isFinishing) {
                when (result) {
                    is Result.Loading -> showLoading()
                    is Result.Success -> {
                        hideLoading()
                        showSuccessDialog(
                            R.string.success_title,
                            R.string.auth_success_notion,
                            onSuccess = {
                                val intent = Intent(this, NavigationBarView::class.java)
                                startActivity(intent)
                            })
                    }
                    is Result.Error -> {
                        showErrorDialog(R.string.error_title, R.string.login_failed)
                        hideLoading()
                    }
                }
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }
}