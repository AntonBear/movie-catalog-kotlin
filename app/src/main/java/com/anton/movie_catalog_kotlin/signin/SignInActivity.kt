package com.anton.movie_catalog_kotlin.signin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.anton.movie_catalog_kotlin.MainActivity
import com.anton.movie_catalog_kotlin.R
import com.anton.movie_catalog_kotlin.bottomNavigation.BottomNavigationView
import com.anton.movie_catalog_kotlin.databinding.ActivitySignInBinding
import com.anton.movie_catalog_kotlin.utils.showErrorDialog
import kotlinx.coroutines.launch

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private val viewModel: SignInViewModel by viewModels { SignInViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)

        binding.squareButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->

                    binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

                    binding.signInButton.text = if (state.isLoading) getString(R.string.sign_in_progress) else getString(R.string.sign_in)

                    binding.signInButton.isEnabled = state.isAuthButtonEnabled

                    when (state.error) {
                        is SignInError.InvalidFields -> showErrorDialog(
                            R.string.error_title,
                            getString(R.string.error_valid)
                        )
                        is SignInError.RequestError -> showErrorDialog(
                            R.string.error_title,
                            state.error.message ?: getString(R.string.error_default)
                        )
                        else -> Unit
                    }

                    if (state.isUserLoggedIn) {
                        val intent = Intent(this@SignInActivity, BottomNavigationView::class.java)
                        startActivity(intent)
                    }
                    viewModel.onHandleError()
                }

            }
        }
    }
}




