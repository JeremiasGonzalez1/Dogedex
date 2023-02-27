package com.jeredev.dogedex.auth

import android.content.Context
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jeredev.dogedex.R
import com.jeredev.dogedex.databinding.FragmentSingUpBinding


class SingUpFragment : Fragment() {
    interface SingUpFragmentActions {
        fun onFieldsValidated(email: String, password: String, repeatPassword: String)
    }

    lateinit var singUpFragmentActions: SingUpFragmentActions

    override fun onAttach(context: Context) {
        super.onAttach(context)
        singUpFragmentActions = try {
            context as SingUpFragmentActions
        } catch (e: java.lang.ClassCastException) {
            throw ClassCastException("$context must implement Login Fragment first")
        }
    }

    lateinit var binding: FragmentSingUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSingUpBinding.inflate(inflater, container, false)
        setSingUpButton()
        return binding.root
    }

    private fun setSingUpButton() {
        binding.btnSingUp.setOnClickListener {
            validateFields()
        }
    }

    private fun validateFields() {
        binding.inputUserNew.error = ""
        binding.inputPasswordNew.error = ""
        binding.inputRepeatPasswordNew.error = ""

        val email = binding.inputUserNew.editText?.text.toString()

        if (!isValidEmail(email)) {
            binding.inputUserNew.error = getString(R.string.email_is_not_valid)
            return
        }

        val password = binding.inputPasswordNew.editText?.text.toString()

        if (password.isEmpty()) {
            binding.inputPasswordNew.error = getString(R.string.password_must_not_be_empty)
            return
        }

        val passwordRepeat = binding.inputRepeatPasswordNew.editText?.text.toString()

        if (passwordRepeat.isEmpty()) {
            binding.inputRepeatPasswordNew.error = getString(R.string.password_must_not_be_empty)
            return
        }

        if (password != passwordRepeat) {
            binding.inputPasswordNew.error = getString(R.string.password_do_not_match)
            return
        }

        singUpFragmentActions.onFieldsValidated(email, password, passwordRepeat)
    }

    private fun isValidEmail(email: String): Boolean {
        return !email.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}