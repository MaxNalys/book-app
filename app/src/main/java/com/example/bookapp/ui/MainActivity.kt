package com.example.bookapp.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookapp.R
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

        // Ініціалізація ViewModel
        val factory = BookViewModelFactory(BookRepository(RetrofitInstance.apiService))
        bookViewModel = ViewModelProvider(this, factory).get(BookViewModel::class.java)

        // Ініціалізація RecyclerView
        val fiveStarsRecyclerView: RecyclerView = findViewById(R.id.fiveStarsBooks)
        val fourStarsRecyclerView: RecyclerView = findViewById(R.id.fourStarsBooks)
        val threeStarsRecyclerView: RecyclerView = findViewById(R.id.threeStarsBooks)
        val twoStarsRecyclerView: RecyclerView = findViewById(R.id.twoStarsBooks)
        val oneStarRecyclerView: RecyclerView = findViewById(R.id.oneStarBooks)

        // Перевірка наявності LayoutManager перед встановленням нового
        setUpRecyclerView(fiveStarsRecyclerView)
        setUpRecyclerView(fourStarsRecyclerView)
        setUpRecyclerView(threeStarsRecyclerView)
        setUpRecyclerView(twoStarsRecyclerView)
        setUpRecyclerView(oneStarRecyclerView)

        // Створення адаптерів для кожного RecyclerView
        fiveStarsAdapter = BookAdapter()
        fourStarsAdapter = BookAdapter()
        threeStarsAdapter = BookAdapter()
        twoStarsAdapter = BookAdapter()
        oneStarAdapter = BookAdapter()

        // Призначення адаптерів для відповідних RecyclerView
        fiveStarsRecyclerView.adapter = fiveStarsAdapter
        fourStarsRecyclerView.adapter = fourStarsAdapter
        threeStarsRecyclerView.adapter = threeStarsAdapter
        twoStarsRecyclerView.adapter = twoStarsAdapter
        oneStarRecyclerView.adapter = oneStarAdapter

        // Спостереження за даними
        bookViewModel.books.observe(this, Observer { books ->
            if (books != null && books.isNotEmpty()) {
                Log.d("MainActivity", "Books updated in RecyclerView: ${books.size}")

                // Фільтрація книг за рейтингом
                val fiveStarsBooks = books.filter { it.bookRank == 15 }
                val fourStarsBooks = books.filter { it.bookRank in 12..14 }
                val threeStarsBooks = books.filter { it.bookRank in 9..11 }
                val twoStarsBooks = books.filter { it.bookRank in 6..8 }
                val oneStarBooks = books.filter { it.bookRank <= 5 }

                // Оновлення адаптерів
                fiveStarsAdapter.submitList(fiveStarsBooks)
                fourStarsAdapter.submitList(fourStarsBooks)
                threeStarsAdapter.submitList(threeStarsBooks)
                twoStarsAdapter.submitList(twoStarsBooks)
                oneStarAdapter.submitList(oneStarBooks)
            } else {
                Log.d("MainActivity", "No books available or received null data")
            }
        })

        // Запуск завантаження даних
      // Якщо є такий метод для завантаження даних
    }

    // Функція для перевірки та налаштування LayoutManager
    private fun setUpRecyclerView(recyclerView: RecyclerView) {
        if (recyclerView.layoutManager == null) {
            // Налаштовуємо LayoutManager, якщо його ще не було
            val horizontalLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.layoutManager = horizontalLayoutManager
        }
    }
}
