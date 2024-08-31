package com.nicolascristaldo.bookshelf.data

import androidx.annotation.StringRes
import com.nicolascristaldo.bookshelf.R

enum class BookshelfDestinations(@StringRes val title: Int) {
    Home(title = R.string.app_name),
    BookDetails(title = R.string.book_details)
}