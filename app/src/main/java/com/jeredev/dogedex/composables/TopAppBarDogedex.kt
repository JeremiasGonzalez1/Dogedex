package com.jeredev.dogedex.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.jeredev.dogedex.R

@Composable
fun DogTopAppBarWithArrowBack(onClick: () -> Unit, text: String) {
    TopAppBar(
        title = {
            Text(
                text = text
            )
        },
        backgroundColor = Color.White,
        contentColor = Color.Black,
        navigationIcon = { BackNavigationIcon(onClick) }
    )
}

@Composable
fun DogTopAppBar(text: String) {
    TopAppBar(
        title = {
            Text(
                text = text
            )
        },
        backgroundColor = Color.White,
        contentColor = Color.Black,
    )
}

@Composable
fun BackNavigationIcon(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            painter = rememberVectorPainter(image = Icons.Sharp.ArrowBack),
            contentDescription = null
        )
    }
}