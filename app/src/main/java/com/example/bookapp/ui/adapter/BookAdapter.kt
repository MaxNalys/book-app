package com.example.bookapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookapp.R
import com.example.bookapp.data.model.Book

class BookAdapter : ListAdapter<Book, BookAdapter.BookViewHolder>(BookDiffCallback()) {

    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.bookTitle)
        val author: TextView = itemView.findViewById(R.id.bookAuthor)
        val image: ImageView = itemView.findViewById(R.id.bookImage)
        val stars: List<ImageView> = listOf(
            itemView.findViewById(R.id.star1),
            itemView.findViewById(R.id.star2),
            itemView.findViewById(R.id.star3),
            itemView.findViewById(R.id.star4),
            itemView.findViewById(R.id.star5)
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = getItem(position)
        holder.title.text = book.bookTitle
        holder.author.text = book.bookAuthor
        Glide.with(holder.image.context)
            .load(book.bookImage)
            .into(holder.image)

        // Оновлення зірочок на основі рейтингу
        updateRatingStars(holder, book.bookRank)
    }

    // Функція для оновлення зірочок
    private fun updateRatingStars(holder: BookViewHolder, rating: Int) {
        // Максимальна кількість зірочок = 5, кожна зірочка = 3 бали (для 15-бального рейтингу)
        val starsToFill = (rating / 3) // Оскільки максимальний рейтинг 15, кожна зірочка отримує 3 бали

        for (i in 0 until 5) {
            val star = holder.stars[i]
            if (i < starsToFill) {
                star.setImageResource(R.drawable.ic_star_filled)  // Заповнена зірочка
            } else {
                star.setImageResource(R.drawable.ic_star_empty)  // Порожня зірочка
            }
        }
    }

    class BookDiffCallback : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.bookIsbn == newItem.bookIsbn
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }
    }
}
