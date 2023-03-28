package com.jeredev.dogedex.doglist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.ExperimentalMaterialApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.jeredev.dogedex.R
import com.jeredev.dogedex.api.response.ApiResponseStatus
import com.jeredev.dogedex.databinding.ActivityDogListBinding
import com.jeredev.dogedex.dogdetail.DogDetailActivity
import com.jeredev.dogedex.dogdetail.DogDetailActivity.Companion.DOG_KEY
import com.jeredev.dogedex.jetpackcompose.detaildog.DogDetailComposeActivity
import com.jeredev.dogedex.jetpackcompose.ui.theme.DogedexTheme
import com.jeredev.dogedex.model.Dog


@ExperimentalMaterialApi
class DogListActivity : ComponentActivity() {

    private val viewModel: DogListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val status = viewModel.status
            DogedexTheme {
                val listDog = listOf<Dog>()
                DogListScreen(
                    viewModel.dogList.value,
                    onItemClickListener = ::openDogDetailActivity,
                    onNavigationBack = ::onNavigationBack,
                    status = status.value,
                    onErrorDialogDismiss = ::resetApiResponseStatus
                )
            }
        }
    }

    private fun onNavigationBack() {
        finish()
    }

    private fun openDogDetailActivity(dog: Dog) {
        val intent = Intent(this, DogDetailComposeActivity::class.java)
        intent.putExtra(DogDetailComposeActivity.DOG_KEY, dog)
        startActivity(intent)
    }

    private fun resetApiResponseStatus() {
        viewModel.resetApiResponseStatus()
    }

}