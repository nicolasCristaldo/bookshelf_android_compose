package com.nicolascristaldo.bookshelf.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.nicolascristaldo.bookshelf.R
import com.nicolascristaldo.bookshelf.model.Book
import com.nicolascristaldo.bookshelf.model.Volumes
import com.nicolascristaldo.bookshelf.ui.screens.components.BookImage
import com.nicolascristaldo.bookshelf.ui.screens.components.ErrorScreen
import com.nicolascristaldo.bookshelf.ui.screens.components.LoadingScreen

@Composable
fun HomeScreen(
    bookshelfUiState: BookshelfUiState,
    onClick: () -> Unit,
    viewModel: BookshelfViewModel,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    when(bookshelfUiState) {
        is BookshelfUiState.Success -> BooksGridScreen(
            viewModel = viewModel,
            onClick = onClick,
            volumes = bookshelfUiState.volumes,
            modifier = Modifier
        )
        is BookshelfUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is BookshelfUiState.Error -> ErrorScreen(
            retryAction = viewModel::getBooks,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun BooksGridScreen(
    volumes: Volumes,
    onClick: () -> Unit,
    viewModel: BookshelfViewModel,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(dimensionResource(id = R.dimen.grid_cell_size)),
        modifier = modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium)),
        contentPadding = contentPadding,
    ) {
        items(items = volumes.items, key = { book -> book.id }) { book ->
            BookCard(
                book = book,
                viewModel = viewModel,
                onClick = onClick,
                modifier = modifier
                    .padding(dimensionResource(id = R.dimen.padding_mini))
                    .fillMaxWidth()
                    .aspectRatio(0.7f)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookCard(
    book: Book,
    onClick: () -> Unit,
    viewModel: BookshelfViewModel,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(0.dp),
        onClick = {
            viewModel.changeSelectedBookId(book.id)
            onClick()
        },
        modifier = modifier
    ) {
        book.volumeInfo.imageLink?.let {
            BookImage(
                imgSrc = it.replaceThumbnailHttp
            )
        }
    }
}