package com.nicolascristaldo.bookshelf.model

import kotlinx.serialization.Serializable

@Serializable
data class ImageLink(
    val thumbnail: String
) {
    val replaceThumbnailHttp : String
        get() = thumbnail.replace("http", "https")
}