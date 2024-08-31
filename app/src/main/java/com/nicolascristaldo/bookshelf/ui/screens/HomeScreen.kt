package com.nicolascristaldo.bookshelf.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nicolascristaldo.bookshelf.R
import com.nicolascristaldo.bookshelf.model.Book
import com.nicolascristaldo.bookshelf.model.Volumes
import coil.compose.AsyncImage
import coil.request.ImageRequest

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
        columns = GridCells.Adaptive(200.dp),
        modifier = modifier.padding(horizontal = 4.dp),
        contentPadding = contentPadding,
    ) {
        items(items = volumes.items, key = { book -> book.id }) { book ->
            BookCard(
                book = book,
                viewModel = viewModel,
                onClick = onClick,
                modifier = modifier
                    .padding(6.dp)
                    .fillMaxWidth()
                    .aspectRatio(1.5f)
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
        onClick = {
            viewModel.changeSelectedBookId(book.id)
            onClick()
        }
    ) {
        Column(modifier = Modifier) {
            book.volumeInfo.imageLink?.let { BookImage(imgSrc = it.replaceThumbnailHttp) }
        }
        book.volumeInfo.title?.let { Text(text = it) }
    }
}

@Composable
fun BookImage(
    imgSrc: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(imgSrc)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(id = R.drawable.ic_loading),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun ErrorScreen(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(id = R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(id = R.string.retry))
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(75.dp),
        painter = painterResource(R.drawable.ic_loading),
        contentDescription = null
    )
}