package com.example.bookapp.data.repository

import com.example.bookapp.data.model.Book
import com.example.bookapp.data.remote.ApiService
import retrofit2.Response

class BookRepository(private val apiService: ApiService) {

    suspend fun getBooks(): Response<List<Book>> {
        return apiService.getBooks() // Assuming `getBooks()` is a Retrofit call returning Response<List<Book>>
    }
}
