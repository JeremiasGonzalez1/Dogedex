package com.jeredev.dogedex.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.findNavController
import com.jeredev.dogedex.MainActivity
import com.jeredev.dogedex.R
import com.jeredev.dogedex.api.response.ApiResponseStatus
import com.jeredev.dogedex.databinding.ActivityLoginBinding
import com.jeredev.dogedex.jetpackcompose.ui.theme.DogedexTheme
import com.jeredev.dogedex.model.User

@ExperimentalMaterialApi
class LoginActivity : ComponentActivity(), LoginFragment.LoginFragmentActions,
    SingUpFragment.SingUpFragmentActions {

    private val viewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DogedexTheme {
                //LoginScreen()
                SingUpScreen(onNavigationBack = ::navigateBack)
            }
        }
        /*val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.status.observe(this) {
            when (it) {
                is ApiResponseStatus.Error -> {
                    binding.loadingWheel.visibility = View.GONE
                    showErrorDialog(it.message)
                }
                is ApiResponseStatus.Loading -> binding.loadingWheel.visibility = View.VISIBLE
                is ApiResponseStatus.Success -> binding.loadingWheel.visibility = View.GONE
            }
        }
        viewModel.user.observe(this) { user ->
            if (user != null) {
                User.setLoggedInUser(this, user)
                startMainActivity()
            }
        }*/
    }

    private fun navigateBack() {
        setContent { LoginScreen() }
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun showErrorDialog(message: Int) {
        AlertDialog.Builder(this).setTitle(R.string.there_was_an_error).setMessage(message)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                /**dismis dialos**/
            }.create().show()
    }

    override

    fun onRegisterButtonClick() {
        findNavController(R.id.nav_host_fragment).navigate(R.id.action_loginFragment_to_singUpFragment)
    }

    override fun onLoginFieldsValidated(email: String, password: String) {
        viewModel.login(email, password)
    }

    override fun onFieldsValidated(email: String, password: String, repeatPassword: String) {
        viewModel.singUp(email, password, repeatPassword)
    }
}
