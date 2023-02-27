package com.jeredev.dogedex.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import com.jeredev.dogedex.MainActivity
import com.jeredev.dogedex.R
import com.jeredev.dogedex.api.response.ApiResponseStatus
import com.jeredev.dogedex.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), LoginFragment.LoginFragmentActions,
    SingUpFragment.SingUpFragmentActions {

    private val viewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
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
                startMainActivity()

            }
        }
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
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

    override fun onFieldsValidated(email: String, password: String, repeatPassword: String) {
        viewModel.singUp(email, password, repeatPassword)
    }
}
