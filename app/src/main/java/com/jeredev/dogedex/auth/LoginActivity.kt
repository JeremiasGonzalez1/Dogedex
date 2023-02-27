package com.jeredev.dogedex.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.jeredev.dogedex.R

class LoginActivity : AppCompatActivity(), LoginFragment.LoginFragmentActions, SingUpFragment.SingUpFragmentActions {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun onRegisterButtonClick() {
        findNavController(R.id.nav_host_fragment).navigate(R.id.action_loginFragment_to_singUpFragment)
    }

    override fun onFieldsValidated(email: String, password: String, repeatPassword: String) {

    }
}