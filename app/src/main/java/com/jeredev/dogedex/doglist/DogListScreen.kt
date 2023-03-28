package com.jeredev.dogedex.doglist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jeredev.dogedex.R
import com.jeredev.dogedex.api.response.ApiResponseStatus
import com.jeredev.dogedex.composables.DogTopAppBar
import com.jeredev.dogedex.composables.DogTopAppBarWithArrowBack
import com.jeredev.dogedex.composables.ErrorDialog
import com.jeredev.dogedex.composables.LoadingWheel
import com.jeredev.dogedex.model.Dog

private const val GRID_SPAN_COUNT = 3

@ExperimentalMaterialApi
@Composable
fun DogListScreen(
    dogList: List<Dog>,
    onItemClickListener: (Dog) -> Unit,
    onNavigationBack: () -> Unit,
    status: ApiResponseStatus<Any>?,
    onErrorDialogDismiss: () -> Unit
) {
    Scaffold(
        topBar = { DogTopAppBarWithArrowBack(onNavigationBack, stringResource(R.string.my_dog_collection_app_bar)) }
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(GRID_SPAN_COUNT),
            content = {
                items(dogList) {
                    DogGridItem(it, onItemClickListener)
                }
            }
        )
    }

    if (status is ApiResponseStatus.Loading) {
        LoadingWheel()
    } else if (status is ApiResponseStatus.Error) {
        ErrorDialog(
            status.message,
            onDialogDismiss = onErrorDialogDismiss
        )
    }
}


@ExperimentalMaterialApi
@Composable
fun DogGridItem(dog: Dog, onDogClickListener: (Dog) -> Unit) {
    if (dog.inCollection) {
        Surface(
            modifier = Modifier
                .padding(8.dp)
                .height(100.dp)
                .width(100.dp),
            onClick = { onDogClickListener(dog) },
            shape = RoundedCornerShape(4.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(dog.heightMale)
                    .build(),
                contentDescription = null,
                modifier = Modifier.background(Color.White)
            )
        }
    } else {
        Surface(
            modifier = Modifier
                .padding(8.dp)
                .height(100.dp)
                .width(100.dp),
            color = Color.Red,
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(
                text = dog.index.toString(),
                color = Color.White,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                fontSize = 42.sp,
                fontWeight = FontWeight.Black
            )
        }

    }
}
