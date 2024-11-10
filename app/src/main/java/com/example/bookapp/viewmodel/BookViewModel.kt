package com.example.bookapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookapp.data.model.Book
import com.example.bookapp.data.repository.BookRepository
import kotlinx.coroutines.launch
import retrofit2.Response // Make sure to import Response

class BookViewModel(private val repository: BookRepository) : ViewModel() {

    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> get() = _books

    fun getBooks() {
        viewModelScope.launch {
            try {
                val response: Response<List<Book>> = repository.getBooks() // Use Response<List<Book>>
                if (response.isSuccessful) {
                    _books.value = response.body() ?: emptyList()
                } else {
                    Log.e("BookViewModel", "Error fetching books: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("BookViewModel", "Error: ${e.message}")
            }
        }
    }
}
