package com.nicolascristaldo.bookshelf.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.nicolascristaldo.bookshelf.BookshelfApplication
import com.nicolascristaldo.bookshelf.data.BookshelfRepository
import com.nicolascristaldo.bookshelf.model.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface DetailsUiState {
    data class Success(val book: Book): DetailsUiState
    object Error: DetailsUiState
    object Loading: DetailsUiState
}

class DetailsViewModel(private val bookshelfRepository: BookshelfRepository): ViewModel() {
    private val _detailsUiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
    val detailsUiState = _detailsUiState.asStateFlow()

    fun getBook(id: String) {
        viewModelScope.launch {
            _detailsUiState.value = DetailsUiState.Loading
            _detailsUiState.value = try {
                DetailsUiState.Success(bookshelfRepository.getBook(id))
            } catch(e: IOException) {
                DetailsUiState.Error
            } catch(e: HttpException) {
                DetailsUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BookshelfApplication)
                val bookshelfRepository = application.container.bookshelfRepository
                DetailsViewModel(bookshelfRepository = bookshelfRepository)
            }
        }
    }
}