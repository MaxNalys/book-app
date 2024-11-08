package com.example.bookapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookapp.R
import com.example.bookapp.data.model.Book
import com.example.bookapp.data.remote.RetrofitInstance
import com.example.bookapp.data.repository.BookRepository
import com.example.bookapp.ui.adapter.BookAdapter
import com.example.bookapp.ui.viewmodel.BookViewModel
import com.example.bookapp.ui.viewmodel.BookViewModelFactory

class BooksByRatingActivity : AppCompatActivity() {

    private lateinit var bookViewModel: BookViewModel
    private lateinit var bookAdapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books_by_rating)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewBooksByRating)
        recyclerView.layoutManager = GridLayoutManager(this, 2) // 2 стовпці

        // Ініціалізація ViewModel і Adapter
        val factory = BookViewModelFactory(BookRepository(RetrofitInstance.apiService))
        bookViewModel = ViewModelProvider(this, factory).get(BookViewModel::class.java)
        bookAdapter = BookAdapter { book -> onBookClicked(book) }
        recyclerView.adapter = bookAdapter

        val rating = intent.getIntExtra("rating", 5) // За замовчуванням 5

        // Спостереження за даними книг
        bookViewModel.books.observe(this, Observer { books ->
            books?.let {
                val filteredBooks = it.filter { book -> book.bookRank == rating }
                bookAdapter.submitList(filteredBooks)
            }
        })

        bookViewModel.getBooks()
    }

    private fun onBookClicked(book: Book) {
        val intent = Intent(this, BookDetailActivity::class.java).apply {
            putExtra("BOOK", book)
        }
        startActivity(intent)
    }
}
