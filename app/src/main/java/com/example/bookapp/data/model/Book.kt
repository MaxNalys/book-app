package com.example.bookapp.data.model

// Book.kt
data class Book(
    val bookTitle: String,
    val bookImage: String,
    val bookDescription: String,
    val bookAuthor: String,
    val bookPublisher: String,
    val amazonBookUrl: String,
    val bookIsbn: String,
    val bookRank: Int
)
