package com.jeredev.dogedex.jetpackcompose.detaildog

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jeredev.dogedex.R
import com.jeredev.dogedex.api.response.ApiResponseStatus
import com.jeredev.dogedex.model.Dog

@Composable
fun DogDetailScreen(
    dog: Dog,
    status: ApiResponseStatus<Any>?,
    onButtonClicked: () -> Unit,
    onErrorDialogDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.secondary_background))
            .padding(start = 8.dp, end = 8.dp, bottom = 16.dp),
        contentAlignment = Alignment.TopCenter
    ) {

        DogInformation(dog)
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(dog.heightMale)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(250.dp)
                .width(250.dp)
                .padding(top = 80.dp)
        )
        FloatingActionButton(
            modifier = Modifier.align(alignment = Alignment.BottomCenter),
            onClick = { onButtonClicked() }) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = null
            )

        }
        if (status is ApiResponseStatus.Loading) {
            LoadingWheel()
        } else if (status is ApiResponseStatus.Error) {
            ErrorDialog(
                status,
                onDialogDismiss = onErrorDialogDismiss
            )
        }
    }
}

@Composable
fun LoadingWheel() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = colorResource(id = R.color.color_primary))
    }
}

@Composable
fun DogInformation(dog: Dog) {
    Log.i("hola", "$dog ")
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 180.dp)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(4.dp),
            color = colorResource(id = R.color.white)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.dog_index_format, dog.index),
                    fontSize = 32.sp, color = colorResource(id = R.color.text_black),
                    textAlign = TextAlign.End
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp, bottom = 8.dp),
                    text = dog.temperament,
                    fontSize = 32.sp, color = colorResource(id = R.color.text_black),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium
                )

                LifeIcon()

                Text(
                    stringResource(id = R.string.dog_life_expectancy_format, dog.imageUrl),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.text_black)
                )

                Text(
                    text = dog.lifeExpectancy,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.text_black),
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Divider(
                    modifier = Modifier.padding(
                        top = 8.dp,
                        start = 8.dp,
                        end = 8.dp,
                        bottom = 16.dp
                    ),
                    color = colorResource(id = R.color.divider),
                    thickness = 1.dp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ColumInfo(modifier = Modifier.weight(1f), dog)

                    VerticalDivider()

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = dog.name,
                            fontSize = 16.sp,
                            color = colorResource(id = R.color.text_black),
                            modifier = Modifier.padding(top = 8.dp),
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = stringResource(id = R.string.group),
                            textAlign = TextAlign.Center,
                            color = colorResource(id = R.color.light_gray),
                            modifier = Modifier.padding(top = 8.dp),
                            fontSize = 16.sp
                        )

                    }

                    VerticalDivider()

                    ColumInfo(modifier = Modifier.weight(1f), dog)
                }

            }

        }
    }
}

@Composable
private fun VerticalDivider() {
    Divider(
        modifier = Modifier
            .height(
                42.dp
            )
            .width(1.dp),
        color = colorResource(id = R.color.divider)

    )
}

@Composable
private fun ColumInfo(modifier: Modifier, dog: Dog) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.female),
            color = colorResource(id = R.color.text_black),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            text = dog.weightFemale,
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.text_black),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.weight),
            color = colorResource(id = R.color.light_gray),
            modifier = Modifier.padding(top = 8.dp),
        )

        Text(
            text = dog.weightMale,
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.text_black),
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            modifier = Modifier.padding(
                top = 8.dp
            )
        )

        Text(
            text = stringResource(id = R.string.height),
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.light_gray),
            modifier = Modifier.padding(top = 8.dp)
        )

    }
}

@Composable
fun ErrorDialog(
    status: ApiResponseStatus.Error<Any>,
    onDialogDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {},
        title = {
            Text(text = stringResource(R.string.error_dialog_tittle))
        },
        text = {
            Text(
                stringResource(id = status.message)
            )
        },
        confirmButton = {
            Button(onClick = { onDialogDismiss() }) {
                Text(text = stringResource(R.string.try_again_alert_dialog))
            }
        }
    )
}

@Composable
fun LifeIcon() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 80.dp, end = 80.dp)
    ) {
        Surface(
            shape = CircleShape,
            color = colorResource(id = R.color.color_primary)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_hearth_white),
                contentDescription = null,
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .padding(4.dp)
            )
        }

        Surface(
            shape = RoundedCornerShape(bottomEnd = 2.dp, topEnd = 2.dp),
            modifier = Modifier
                .width(200.dp)
                .height(6.dp),
            color = colorResource(id = R.color.color_primary)
        ) {

        }
    }
}

