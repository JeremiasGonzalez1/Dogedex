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
class LoginActivity : ComponentActivity() {

    private val viewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val user = viewModel.user
            val userValue = user.value

            if (userValue != null) {
                User.setLoggedInUser(this, userValue)
                startMainActivity()
            }

            val status = viewModel.status
            DogedexTheme {
                AuthScreen(
                    onErrorDialogDismiss = ::resetApiResponseStatus,
                    status = status.value,
                    onLoginButtonClick = { email, password -> viewModel.login(email, password) },
                    onRegisterButtonClick = { email, password, passwordConfirmation ->
                        viewModel.singUp(
                            email,
                            password,
                            passwordConfirmation
                        )
                    },
                    viewModel = viewModel
                )
            }
        }
    }


    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun resetApiResponseStatus() {
        viewModel.resetApiResponseStatus()
    }
}
