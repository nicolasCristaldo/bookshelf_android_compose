package com.nicolascristaldo.bookshelf.model

import kotlinx.serialization.Serializable

@Serializable
data class Volumes(
    val items: List<Book>,
    val totalItems: Int
)