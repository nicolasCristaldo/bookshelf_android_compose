package com.nicolascristaldo.bookshelf.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.nicolascristaldo.bookshelf.BookshelfApplication
import com.nicolascristaldo.bookshelf.data.BookshelfRepository
import com.nicolascristaldo.bookshelf.model.Volumes
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface BookshelfUiState {
    data class Success(val volumes: Volumes): BookshelfUiState
    object Error: BookshelfUiState
    object Loading: BookshelfUiState
}

class BookshelfViewModel(private val bookshelfRepository: BookshelfRepository): ViewModel() {
    var bookshelfUiState: BookshelfUiState by mutableStateOf(BookshelfUiState.Loading)
        private set

    var selectedBookId by mutableStateOf("")

    init {
        getBooks()
    }

    fun changeSelectedBookId(newId: String) {
        selectedBookId = newId
    }

    fun getBooks() {
        viewModelScope.launch {
            bookshelfUiState = BookshelfUiState.Loading
            bookshelfUiState = try {
                BookshelfUiState.Success(bookshelfRepository.getBooks())
            } catch(e: IOException) {
                BookshelfUiState.Error
            } catch(e: HttpException) {
                BookshelfUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BookshelfApplication)
                val bookshelfRepository = application.container.bookshelfRepository
                BookshelfViewModel(bookshelfRepository = bookshelfRepository)
            }
        }
    }
}