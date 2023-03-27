package com.jeredev.dogedex.jetpackcompose.views.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.annotation.ExperimentalCoilApi
import com.jeredev.dogedex.jetpackcompose.views.detail.ListDogs

@OptIn(ExperimentalCoilApi::class)
@Composable
fun MainScreen() {
    Scaffold(topBar = { MainAppBar() }) { padding ->
        ListDogs(modifier = Modifier.padding(padding))
    }
}
