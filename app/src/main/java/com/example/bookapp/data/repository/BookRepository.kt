package com.example.bookapp.data.repository

import android.util.Log
import com.example.bookapp.data.model.Book
import com.example.bookapp.data.remote.ApiService

// BookRepository.kt
class BookRepository(private val apiService: ApiService) {

    suspend fun getBooks(): List<Book> {
        return try {
            val response = apiService.getBooks()
            Log.d("BookRepository", "Received books: ${response.size}")
            response
        } catch (e: Exception) {
            Log.e("BookRepository", "Error fetching books", e)
            emptyList()  // Повертаємо порожній список при помилці
        }
    }
}

