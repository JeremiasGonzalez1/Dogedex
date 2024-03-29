package com.jeredev.dogedex.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.material.ExperimentalMaterialApi
import com.jeredev.dogedex.R
import com.jeredev.dogedex.auth.LoginActivity
import com.jeredev.dogedex.databinding.ActivitySettingsBinding
import com.jeredev.dogedex.model.User

@ExperimentalMaterialApi
class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loggoutButton.setOnClickListener {
            logout()
        }
    }

    private fun logout() {
        User.logout(this)
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}