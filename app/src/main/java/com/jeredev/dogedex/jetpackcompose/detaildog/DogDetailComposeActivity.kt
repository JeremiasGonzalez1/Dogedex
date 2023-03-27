package com.jeredev.dogedex.jetpackcompose.detaildog

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jeredev.dogedex.R
import com.jeredev.dogedex.dogdetail.DogDetailActivity
import com.jeredev.dogedex.doglist.DogListViewModel
import com.jeredev.dogedex.jetpackcompose.detaildog.ui.theme.DogedexTheme
import com.jeredev.dogedex.model.Dog

class DogDetailComposeActivity : ComponentActivity() {

    companion object {
        const val DOG_KEY = "dog"
    }

    val viewModel: DogListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dog = intent?.extras?.getParcelable<Dog>(DOG_KEY)
        if (dog == null) {
            Toast.makeText(this, R.string.error_showing_dog_not_found, Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setContent {
            val status = viewModel.state
            DogedexTheme {
                DogDetailScreen(
                    dog = dog,
                    status = status.value,
                    onButtonClicked = { onButtonClicked() },
                    onErrorDialogDismiss = ::resetApiResponseStatus
                )
            }
        }
    }

    private fun resetApiResponseStatus() {
        viewModel.resetApiResponseStatus()
    }

    private fun onButtonClicked() {
        finish()
    }
}
