package com.nicolascristaldo.bookshelf.ui.screens.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nicolascristaldo.bookshelf.model.Book
import com.nicolascristaldo.bookshelf.ui.screens.BookImage
import com.nicolascristaldo.bookshelf.ui.screens.ErrorScreen
import com.nicolascristaldo.bookshelf.ui.screens.LoadingScreen

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    when(val detailsUiState = viewModel.detailsUiState.collectAsState().value) {
        is DetailsUiState.Success -> BookInformationScreen(
            book = detailsUiState.book,
            modifier = Modifier.fillMaxSize()
        )
        is DetailsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is DetailsUiState.Error -> ErrorScreen(
            retryAction = retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun BookInformationScreen(
    book: Book,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
    ) {
        book.volumeInfo.imageLink?.let { BookImage(imgSrc = it.replaceThumbnailHttp) }
        book.volumeInfo.title?.let { Text(text = it) }
        book.volumeInfo.description?.let { Text(text = it) }
    }
}