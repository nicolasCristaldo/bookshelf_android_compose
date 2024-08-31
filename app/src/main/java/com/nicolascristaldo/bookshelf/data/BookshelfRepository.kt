package com.nicolascristaldo.bookshelf.data

import com.nicolascristaldo.bookshelf.model.Book
import com.nicolascristaldo.bookshelf.model.Volumes
import com.nicolascristaldo.bookshelf.network.BookshelfApiService

interface BookshelfRepository {
    suspend fun getBooks(): Volumes
    suspend fun getBook(id: String): Book
}

class NetworkBookshelfRepository(
    private val bookshelfApiService: BookshelfApiService
): BookshelfRepository {
    override suspend fun getBooks(): Volumes = bookshelfApiService.getBooks()
    override suspend fun getBook(id: String): Book = bookshelfApiService.getBook(id)
}