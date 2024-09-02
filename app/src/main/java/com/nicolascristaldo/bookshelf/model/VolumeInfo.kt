package com.nicolascristaldo.bookshelf.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VolumeInfo(
    val authors: List<String>,
    val categories: List<String>,
    val description: String?,
    @SerialName(value = "imageLinks")
    val imageLink: ImageLink? = null,
    val language: String?,
    val publishedDate: String?,
    val publisher: String?,
    val subtitle: String?,
    val title: String?
) {
    val authorsString: String
        get() = getAuthors()

    val categoriesString: String
        get() = getCategories()

    fun getAuthors(): String {
        var result = ""
        for (author in authors) {
            result += "$author, "
        }
        return result.trimEnd(',', ' ')
    }

    fun getCategories(): String {
        var result = ""
        for (category in categories) {
            result += "$category, "
        }
        return result.trimEnd(',', ' ')
    }
}