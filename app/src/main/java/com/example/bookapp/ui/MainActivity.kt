package com.example.bookapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.viewModels
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
import com.example.bookapp.viewmodel.BookViewModel
import com.example.bookapp.viewmodel.BookViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var fiveStarsAdapter: BookAdapter
    private lateinit var fourStarsAdapter: BookAdapter
    private lateinit var threeStarsAdapter: BookAdapter
    private lateinit var twoStarsAdapter: BookAdapter
    private lateinit var oneStarAdapter: BookAdapter
    private lateinit var bookViewModel: BookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val factory = BookViewModelFactory(BookRepository(RetrofitInstance.apiService))
        bookViewModel = ViewModelProvider(this, factory).get(BookViewModel::class.java)

        // Ініціалізація RecyclerView з GridLayoutManager
        val fiveStarsRecyclerView: RecyclerView = findViewById(R.id.fiveStarsBooks)
        val fourStarsRecyclerView: RecyclerView = findViewById(R.id.fourStarsBooks)
        val threeStarsRecyclerView: RecyclerView = findViewById(R.id.threeStarsBooks)
        val twoStarsRecyclerView: RecyclerView = findViewById(R.id.twoStarsBooks)
        val oneStarRecyclerView: RecyclerView = findViewById(R.id.oneStarBooks)

        // Налаштування горизонтальних списків для кожного RecyclerView
        setUpRecyclerView(fiveStarsRecyclerView, 1, GridLayoutManager.HORIZONTAL)
        setUpRecyclerView(fourStarsRecyclerView, 1, GridLayoutManager.HORIZONTAL)
        setUpRecyclerView(threeStarsRecyclerView, 1, GridLayoutManager.HORIZONTAL)
        setUpRecyclerView(twoStarsRecyclerView, 1, GridLayoutManager.HORIZONTAL)
        setUpRecyclerView(oneStarRecyclerView, 1, GridLayoutManager.HORIZONTAL)

        // Адаптери для кожного рейтингу
        fiveStarsAdapter = BookAdapter { book -> onBookClicked(book) }
        fourStarsAdapter = BookAdapter { book -> onBookClicked(book) }
        threeStarsAdapter = BookAdapter { book -> onBookClicked(book) }
        twoStarsAdapter = BookAdapter { book -> onBookClicked(book) }
        oneStarAdapter = BookAdapter { book -> onBookClicked(book) }

        // Призначення адаптерів RecyclerView
        fiveStarsRecyclerView.adapter = fiveStarsAdapter
        fourStarsRecyclerView.adapter = fourStarsAdapter
        threeStarsRecyclerView.adapter = threeStarsAdapter
        twoStarsRecyclerView.adapter = twoStarsAdapter
        oneStarRecyclerView.adapter = oneStarAdapter

        // Спостерігач за змінами в даних книг
        bookViewModel.books.observe(this, Observer { books ->
            if (books != null && books.isNotEmpty()) {
                Log.d("MainActivity", "Books updated in RecyclerView: ${books.size}")

                val fiveStarsBooks = books.filter { it.bookRank == 15 }
                val fourStarsBooks = books.filter { it.bookRank in 12..14 }
                val threeStarsBooks = books.filter { it.bookRank in 9..11 }
                val twoStarsBooks = books.filter { it.bookRank in 6..8 }
                val oneStarBooks = books.filter { it.bookRank <= 5 }

                fiveStarsAdapter.submitList(fiveStarsBooks)
                fourStarsAdapter.submitList(fourStarsBooks)
                threeStarsAdapter.submitList(threeStarsBooks)
                twoStarsAdapter.submitList(twoStarsBooks)
                oneStarAdapter.submitList(oneStarBooks)
            } else {
                Log.d("MainActivity", "No books available or received null data")
            }
        })

        bookViewModel.getBooks()

        // Обробка натискання на кнопку для відкриття списку книг за рейтингом
        findViewById<Button>(R.id.moreFiveStarsBooks).setOnClickListener {
            openBooksByRatingActivity(15)
        }

        findViewById<Button>(R.id.moreFourStarsBooks).setOnClickListener {
            openBooksByRatingActivity(12)
        }

        findViewById<Button>(R.id.moreThreeStarsBooks).setOnClickListener {
            openBooksByRatingActivity(9)
        }

        findViewById<Button>(R.id.moreTwoStarsBooks).setOnClickListener {
            openBooksByRatingActivity(6)
        }

        findViewById<Button>(R.id.moreOneStarsBooks).setOnClickListener {
            openBooksByRatingActivity(1)
        }
    }

    // Відкриває активність для перегляду книг за рейтингом
    private fun openBooksByRatingActivity(rating: Int) {
        val intent = Intent(this, BooksByRatingActivity::class.java)
        intent.putExtra("rating", rating)
        startActivity(intent)
    }

    // Обробка натискання на книгу
    private fun onBookClicked(book: Book) {
        val intent = Intent(this, BookDetailActivity::class.java).apply {
            putExtra("BOOK", book)
        }
        startActivity(intent)
    }

    // Налаштування RecyclerView
    private fun setUpRecyclerView(recyclerView: RecyclerView, spanCount: Int, orientation: Int) {
        val layoutManager = GridLayoutManager(this, spanCount)
        layoutManager.orientation = orientation
        recyclerView.layoutManager = layoutManager
    }
}
