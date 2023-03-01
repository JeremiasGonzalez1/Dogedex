package com.jeredev.dogedex.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Dog(
    val id: Int,
    val index: Int,
    val temperament: String,
    val name: String,
    val type: String,
    val heightFemale: String,
    val heightMale: String,
    val imageUrl: String,
    val lifeExpectancy: String,
    val weightFemale: String,
    val weightMale: String,
    var inCollection: Boolean = true
) : Parcelable, Comparable<Dog> {
    override fun compareTo(other: Dog) = if (this.index > other.index) 1 else -1


}