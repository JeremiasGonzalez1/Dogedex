package com.jeredev.dogedex.jetpackcompose.views.main

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.jeredev.dogedex.jetpackcompose.ui.theme.DogedexTheme

@Composable
fun BaseAppDoguedex(content: @Composable () -> Unit) {
    DogedexTheme {
        Surface(color = MaterialTheme.colors.background) {
            content()
        }
    }
}