package com.nicolascristaldo.bookshelf.network

import com.nicolascristaldo.bookshelf.model.Book
import com.nicolascristaldo.bookshelf.model.Volumes
import retrofit2.http.GET
import retrofit2.http.Path

interface BookshelfApiService {

    @GET("volumes?q=lalalalalala")
    suspend fun getBooks(): Volumes

    @GET("volumes/{id}")
    suspend fun getBook(@Path("id") id: String): Book
}