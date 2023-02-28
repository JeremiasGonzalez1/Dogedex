package com.jeredev.dogedex.auth

import android.content.Context
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jeredev.dogedex.R
import com.jeredev.dogedex.databinding.FragmentLoginBinding
import com.jeredev.dogedex.isValidEmail

class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding

    interface LoginFragmentActions {
        fun onRegisterButtonClick()
        fun onLoginFieldsValidated(email: String, password: String)
    }

    lateinit var loginFragmentActions: LoginFragmentActions

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginFragmentActions = try {
            context as LoginFragmentActions
        } catch (e: java.lang.ClassCastException) {
            throw ClassCastException("$context must implement Login Fragment first")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        setListeners()
        return binding.root
    }

    private fun setListeners() {
        binding.btnRegister.setOnClickListener {
            loginFragmentActions.onRegisterButtonClick()
        }
        binding.btnLogin.setOnClickListener {
            validateFields()
        }
    }

    private fun validateFields() {
        binding.inputUser.error = ""
        binding.inputPassword.error = ""

        val email = binding.inputUser.editText?.text.toString()

        if (!isValidEmail(email)) {
            binding.inputUser.error = getString(R.string.email_is_not_valid)
            return
        }

        val password = binding.inputPassword.editText?.text.toString()
        if (password.isEmpty()) {
            binding.inputPassword.error = getString(R.string.password_must_not_be_empty)
            return
        }

        loginFragmentActions.onLoginFieldsValidated(email, password)
    }


}