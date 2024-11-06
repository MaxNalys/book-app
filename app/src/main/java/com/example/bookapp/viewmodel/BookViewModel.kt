package com.example.bookapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookapp.data.model.Book
import com.example.bookapp.data.repository.BookRepository
import kotlinx.coroutines.launch

class BookViewModel(private val bookRepository: BookRepository) : ViewModel() {

    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> get() = _books

    init {
        fetchBooks()
    }

    private fun fetchBooks() {
        viewModelScope.launch {
            Log.d("BookViewModel", "Fetching books...")
            val bookList = bookRepository.getBooks()
            if (bookList.isNotEmpty()) {
                _books.postValue(bookList)
                Log.d("BookViewModel", "Books fetched successfully: ${bookList.size}")
            } else {
                Log.d("BookViewModel", "No books found.")
            }
        }
    }
}
