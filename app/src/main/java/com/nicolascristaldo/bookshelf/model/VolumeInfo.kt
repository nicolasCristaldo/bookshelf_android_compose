package com.nicolascristaldo.bookshelf.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VolumeInfo(
    val authors: List<String>?,
    val categories: List<String>?,
    val description: String?,
    @SerialName(value = "imageLinks")
    val imageLink: ImageLink? = null,
    val language: String?,
    val publishedDate: String?,
    val publisher: String?,
    val subtitle: String?,
    val title: String?
)