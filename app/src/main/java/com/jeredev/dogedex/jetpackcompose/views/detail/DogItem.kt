package com.jeredev.dogedex.jetpackcompose


data class DogItem(
    val id: Int,
    val title: String,
    val thumb: String,
    val type: Type
)

const val THUMB_URL = "https://loremflickr.com/200/200/cat?lock="

enum class Type { PHOTO, VIDEO }

fun getDogs() = (1..10).map {
    DogItem(
        id = it,
        title = "title $it",
        thumb = "$THUMB_URL + $it",
        type = if (it % 3 == 0) Type.VIDEO else Type.PHOTO
    )
}
