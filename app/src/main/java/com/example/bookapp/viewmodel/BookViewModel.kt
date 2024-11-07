package com.example.bookapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookapp.data.model.Book
import com.example.bookapp.data.repository.BookRepository
import kotlinx.coroutines.launch

class BookViewModel(private val repository: BookRepository) : ViewModel() {

    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> = _books

    // Отримати всі книги з репозиторію
    fun getBooks() {
        // Launching the suspend function in a coroutine scope
        viewModelScope.launch {
            try {
                val result = repository.getBooks() // This will call the suspend function
                _books.value = result // Update LiveData with the result
            } catch (e: Exception) {
                Log.e("BookViewModel", "Error fetching books", e)
                _books.value = emptyList() // Return empty list in case of error
            }
        }
    }

    // Фільтрація книг за рейтингом
    fun filterBooksByRank(rank: Int) {
        _books.value = _books.value?.filter { it.bookRank == rank }
    }
}
