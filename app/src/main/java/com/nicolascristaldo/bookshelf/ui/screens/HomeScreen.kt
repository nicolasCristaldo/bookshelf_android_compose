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
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nicolascristaldo.bookshelf.R
import com.nicolascristaldo.bookshelf.model.Book
import com.nicolascristaldo.bookshelf.model.Volumes
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nicolascristaldo.bookshelf.model.ImageLink
import com.nicolascristaldo.bookshelf.model.VolumeInfo
import com.nicolascristaldo.bookshelf.ui.theme.BookshelfTheme

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
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.padding(horizontal = 8.dp),
        contentPadding = contentPadding,
    ) {
        items(items = volumes.items, key = { book -> book.id }) { book ->
            BookCard(
                book = book,
                viewModel = viewModel,
                onClick = onClick,
                modifier = modifier
                    .padding(2.dp)
                    .fillMaxWidth()
                    //.aspectRatio(0.8f)
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
        Column(
            modifier = Modifier
        ) {
            book.volumeInfo.imageLink?.let { BookImage(imgSrc = it.replaceThumbnailHttp) }
        }
        book.volumeInfo.title?.let {
            Text(
                text = it,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        }
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

/*@Preview(showBackground = true)
@Composable
fun BookCardPreview() {
    BookshelfTheme {
        val mockdata = Book(
                id = "1",
                VolumeInfo(
                title = "la copa del mundo",
                imageLink = ImageLink(""),
                authors = emptyList(),
                categories = emptyList(),
                description = "",
                language = "",
                publishedDate = "",
                publisher = "",
                subtitle = ""
            )
        )
        BookCard(book = mockdata, onClick = {  })
    }
}

@Preview(showBackground = true)
@Composable
fun BookGridScreenPreview() {
    BookshelfTheme {
        val mockData = Volumes(
            items = List(10){
                Book(
                    id = "1",
                    VolumeInfo(
                        title = "la copa del mundo",
                        imageLink = ImageLink(""),
                        authors = emptyList(),
                        categories = emptyList(),
                        description = "",
                        language = "",
                        publishedDate = "",
                        publisher = "",
                        subtitle = ""
                    )
                )
            },
            totalItems = 10
        )
        BooksGridScreen(
            volumes = mockData,
            onClick = {},
            //viewModel = viewModel,
        )
    }
}*/