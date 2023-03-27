package com.jeredev.dogedex.jetpackcompose.views.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jeredev.dogedex.jetpackcompose.DogItem
import com.jeredev.dogedex.jetpackcompose.Type
import com.jeredev.dogedex.jetpackcompose.getDogs

@Composable
fun ColumnsImplementsForDog(item: DogItem, modifier: Modifier = Modifier) {

    Card(
        modifier = modifier,
        elevation = 0.dp,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(2.dp, Color.LightGray),
        backgroundColor = MaterialTheme.colors.primary
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                DownloadImage(item)
                if (item.type == Type.VIDEO) {
                    Icon(
                        imageVector = Icons.Default.PlayCircleOutline,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(92.dp)
                            .align(Alignment.Center)
                    )
                }
            }
            SetTitleDog(item)
        }
    }


}

@Composable
private fun SetTitleDog(item: DogItem) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            //.background(MaterialTheme.colors.secondary)
            .padding(top = 12.dp, bottom = 12.dp, start = 8.dp, end = 8.dp)
    ) {
        Text(
            text = item.title,
            style = MaterialTheme.typography.h6.copy(fontSize = 15.sp)
        )
    }
}

@Composable
private fun DownloadImage(item: DogItem) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(item.thumb)
            .crossfade(2000)
            .build(),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}

@ExperimentalCoilApi
@Composable
fun ListDogs(modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        contentPadding = PaddingValues(2.dp),
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.padding(2.dp)
    ) {
        items(getDogs()) { item ->
            ColumnsImplementsForDog(item, modifier = Modifier.padding(4.dp))
        }
    }
}