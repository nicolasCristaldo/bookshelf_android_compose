package com.nicolascristaldo.bookshelf

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nicolascristaldo.bookshelf.data.BookshelfDestinations
import com.nicolascristaldo.bookshelf.ui.screens.BookshelfViewModel
import com.nicolascristaldo.bookshelf.ui.screens.HomeScreen
import com.nicolascristaldo.bookshelf.ui.screens.details.DetailsScreen
import com.nicolascristaldo.bookshelf.ui.screens.details.DetailsViewModel

@Composable
fun BookshelfNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val viewModel: BookshelfViewModel = viewModel(factory = BookshelfViewModel.Factory)

    NavHost(
        navController = navController,
        startDestination = BookshelfDestinations.Home.name,
        modifier = modifier
    ) {

        composable(route = BookshelfDestinations.Home.name) {
            HomeScreen(
                viewModel = viewModel,
                bookshelfUiState = viewModel.bookshelfUiState,
                onClick = {
                    navController.navigate(BookshelfDestinations.BookDetails.name)
                }
            )
        }

        composable(route = BookshelfDestinations.BookDetails.name) {
            val detailsViewModel: DetailsViewModel = viewModel(factory = DetailsViewModel.Factory)
            detailsViewModel.getBook(viewModel.selectedBookId)

            DetailsScreen(
                viewModel = detailsViewModel,
                retryAction = {
                    detailsViewModel.getBook(viewModel.selectedBookId)
                }
            )
        }
    }
}