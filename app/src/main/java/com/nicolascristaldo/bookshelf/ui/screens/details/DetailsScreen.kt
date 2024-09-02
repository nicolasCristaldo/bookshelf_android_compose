package com.nicolascristaldo.bookshelf.ui.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nicolascristaldo.bookshelf.R
import com.nicolascristaldo.bookshelf.model.Book
import com.nicolascristaldo.bookshelf.model.ImageLink
import com.nicolascristaldo.bookshelf.model.VolumeInfo
import com.nicolascristaldo.bookshelf.ui.screens.components.BookImage
import com.nicolascristaldo.bookshelf.ui.screens.components.ErrorScreen
import com.nicolascristaldo.bookshelf.ui.screens.components.LoadingScreen
import com.nicolascristaldo.bookshelf.ui.theme.BookshelfTheme

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
            modifier = Modifier
                .fillMaxSize()
        )
        is DetailsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        else -> { ErrorScreen(retryAction = retryAction, modifier = modifier.fillMaxSize()) }
    }
}

@Composable
fun BookInformationScreen(
    book: Book,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(dimensionResource(id = R.dimen.padding_large))
    ) {
        book.volumeInfo.imageLink?.let {
            BookImage(
                imgSrc = it.replaceThumbnailHttp,
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_large))
            )
        }
        book.volumeInfo.title?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        book.volumeInfo.subtitle?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height)))

        InformationRow(
            type = stringResource(id = R.string.detail_type_authors),
            content = book.volumeInfo.authorsString
        )

        book.volumeInfo.publisher?.let {
            InformationRow(
                type = stringResource(id = R.string.detail_type_publisher),
                content = it
            )
        }

        book.volumeInfo.publishedDate?.let {
            InformationRow(
                type = stringResource(id = R.string.detail_type_published_date),
                content = it
            )
        }

        InformationRow(
            type = stringResource(id = R.string.detail_type_categories),
            content = book.volumeInfo.categoriesString
        )

        book.volumeInfo.language?.let {
            InformationRow(
                type = stringResource(id = R.string.detail_type_language),
                content = it
            )
        }

        Text(
            text = stringResource(id = R.string.detail_type_description),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(top = dimensionResource(id = R.dimen.padding_medium))
                .fillMaxWidth()
        )

        book.volumeInfo.description?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}

@Composable
fun InformationRow(
    type: String,
    content: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(bottom = dimensionResource(id = R.dimen.padding_medium))
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Text(
            text = type + ": ",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    BookshelfTheme {
        BookInformationScreen(Book(
            id = "1",
            VolumeInfo(
                title = "la copa del mundo",
                imageLink = ImageLink(""),
                authors = listOf("tom stewart", "jim boin"),
                categories = listOf("sports", "recreation"),
                description = "Every four years, the FIFA World Cup captures the global imagination like no other sporting spectacle. With a cumulative television audience of several billion people tuning in to the 2014 World Cup, and an estimated 700 million watching the finals—including more than 25 million in the United States alone—the World Cup is the world’s most-watched sporting event. The Encyclopedia of the FIFA World Cup provides the most comprehensive and up-to-date information available on the history of this incomparable event. An introductory narrative explains the origins and historical progression of the World Cup, while a chronology traces the development of the World Cup since it was first held in 1930. Hundreds of entries cover the players and coaches who have participated in the World Cup and made the most memorable contributions to the event’s history. Additional entries include officials, stadiums, overviews of each major country’s performances, and more. A separate section provides detailed entries for each World Cup finals tournament. Appendixes contain details on every participant in World Cup history, as well as top performers, officials, and World Cup records. Including an indispensable bibliography on the key World Cup texts, Encyclopedia of the FIFA World Cup is an essential reference for soccer fans, players, and researchers alike.",
                language = "Es",
                publishedDate = "2021-4-7",
                publisher = "Scarecrow Press",
                subtitle = "the FIFA World Cup captures the global imagination"
            )
        ))
    }
}