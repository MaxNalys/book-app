package com.example.bookapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.bookapp.R
import com.example.bookapp.data.model.Book

class BookDetailActivity : AppCompatActivity() {

    private lateinit var bookImageView: ImageView
    private lateinit var bookTitleTextView: TextView
    private lateinit var bookAuthorTextView: TextView
    private lateinit var ratingStarsLayout: LinearLayout
    private lateinit var bookDescriptionTextView: TextView
    private lateinit var bookAmazonLinkTextView: TextView
    private lateinit var backButtonImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        // Ініціалізація елементів UI
        bookImageView = findViewById(R.id.bookImageView)
        bookTitleTextView = findViewById(R.id.bookTitleTextView)
        bookAuthorTextView = findViewById(R.id.bookAuthorTextView)
        ratingStarsLayout = findViewById(R.id.ratingStarsLayout)
        bookDescriptionTextView = findViewById(R.id.bookDescriptionTextView)
        bookAmazonLinkTextView = findViewById(R.id.bookAmazonLinkTextView)
        backButtonImageView = findViewById(R.id.backButtonImageView)

        // Отримуємо книгу з Intent
        val book = intent.getSerializableExtra("BOOK") as? Book

        book?.let { bookData ->
            // Відображення деталей книги
            bookTitleTextView.text = bookData.bookTitle
            bookAuthorTextView.text = bookData.bookAuthor
            bookDescriptionTextView.text = bookData.bookDescription

            // Завантаження зображення через Glide
            Glide.with(this)
                .load(bookData.bookImage)
                .placeholder(R.drawable.ic_error) // запасне зображення
                .error(R.drawable.ic_error) // зображення на випадок помилки
                .into(bookImageView)

            // Оновлення зірочок
            updateRatingStars(bookData.bookRank)

            // Встановлюємо лінк на Amazon
            bookAmazonLinkTextView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(bookData.amazonBookUrl))
                startActivity(intent)
            }

            // Кнопка назад
            backButtonImageView.setOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun updateRatingStars(rating: Int) {
        val stars = rating / 3
        val starImages = listOf(
            findViewById<ImageView>(R.id.star1),
            findViewById<ImageView>(R.id.star2),
            findViewById<ImageView>(R.id.star3),
            findViewById<ImageView>(R.id.star4),
            findViewById<ImageView>(R.id.star5)
        )

        for (i in 0 until 5) {
            if (i < stars) {
                starImages[i].setImageResource(R.drawable.ic_star_filled)
            } else {
                starImages[i].setImageResource(R.drawable.ic_star_empty)
            }
        }
    }
}
