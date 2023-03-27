package com.jeredev.dogedex.doglist

import android.content.ClipData.Item
import android.widget.AdapterView.OnItemClickListener
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jeredev.dogedex.R
import com.jeredev.dogedex.jetpackcompose.DogItem
import com.jeredev.dogedex.model.Dog

@Composable
fun DogListScreen(dogList: List<Dog>, onItemClickListener: (Dog) -> Unit) {

    LazyColumn {
        items(dogList) {
            DogItem(it, onItemClickListener)
        }
    }

}

@Composable
fun DogItem(dog: Dog, onDogClickListener: (Dog) -> Unit) {
    if (dog.inCollection) {
        Text(
            modifier = Modifier
                .padding(16.dp)
                .clickable {
                    onDogClickListener(dog)
                }, text = dog.temperament
        )
    } else {
        Text(
            modifier = Modifier
                .padding(16.dp)
                .background(color = Color.Red),
            text = stringResource(id = R.string.dog_index_format, dog.index)
        )
    }
}