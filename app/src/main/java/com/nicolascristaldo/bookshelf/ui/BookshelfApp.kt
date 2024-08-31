package com.nicolascristaldo.bookshelf.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nicolascristaldo.bookshelf.ui.screens.BookshelfViewModel
import com.nicolascristaldo.bookshelf.ui.screens.HomeScreen

@Composable
fun BookshelfApp() {
    val bookshelfViewModel: BookshelfViewModel = viewModel(factory = BookshelfViewModel.Factory)
    HomeScreen(
        bookshelfUiState = bookshelfViewModel.bookshelfUiState,
        retryAction = bookshelfViewModel::getBooks
    )
}