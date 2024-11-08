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

class BookAdapter(private val onBookClick: (Book) -> Unit) : ListAdapter<Book, BookAdapter.BookViewHolder>(BookDiffCallback()) {

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

        init {
            itemView.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onBookClick(getItem(position))  // викликається при кліку на книгу
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = getItem(position)
        holder.title.text = book.bookTitle
        holder.author.text = book.bookAuthor

        // Завантаження зображення через Glide
        Glide.with(holder.image.context)
            .load(book.bookImage)
            .placeholder(R.drawable.ic_error) // запасне зображення
            .error(R.drawable.ic_error) // зображення на випадок помилки
            .into(holder.image)

        updateRatingStars(holder, book.bookRank)
    }

    private fun updateRatingStars(holder: BookViewHolder, rating: Int) {
        val starsToFill = rating / 3
        for (i in 0 until 5) {
            val star = holder.stars[i]
            if (i < starsToFill) {
                star.setImageResource(R.drawable.ic_star_filled)
            } else {
                star.setImageResource(R.drawable.ic_star_empty)
            }
        }
    }

    private class BookDiffCallback : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.bookIsbn == newItem.bookIsbn
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }
    }
}
