package com.example.newsapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.newsapp.R
import com.example.newsapp.databinding.ItemNewsBinding
import com.example.newsapp.domain.model.ArticleDomain

class NewsAdapter(
    private var articles: List<ArticleDomain>,
    bookmarkedTitles: List<String>,
    favouriteTitles: List<String>,
    private val onItemClick: (ArticleDomain) -> Unit,
    private val onBookmarkClick: (ArticleDomain) -> Unit,
    private val onFavouriteClick: (ArticleDomain) -> Unit
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val bookmarkedTitles = bookmarkedTitles.toMutableList()
    private val favouriteTitles = favouriteTitles.toMutableList()

    inner class NewsViewHolder(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: ArticleDomain) {
            binding.tvTitle.text = article.title
            binding.tvDescription.text = article.description
            binding.ivThumbnail.load(article.urlToImage)

            val isBookmarked = bookmarkedTitles.contains(article.title)
            val isFavourited = favouriteTitles.contains(article.title)

            binding.ivBookmark.visibility = View.VISIBLE
            binding.ivFavourite.visibility = View.VISIBLE

            binding.ivBookmark.setImageResource(
                if (isBookmarked) R.drawable.bookmark_black else R.drawable.bookmark
            )

            binding.ivFavourite.setImageResource(
                if (isFavourited) R.drawable.favorite_black else R.drawable.favorite
            )

            binding.root.setOnClickListener { onItemClick(article) }
            binding.ivBookmark.setOnClickListener { onBookmarkClick(article) }
            binding.ivFavourite.setOnClickListener { onFavouriteClick(article) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun getItemCount() = articles.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    fun setData(
        newArticles: List<ArticleDomain>,
        newBookmarks: List<String>,
        newFavourites: List<String>
    ) {
        articles = newArticles
        bookmarkedTitles.clear()
        bookmarkedTitles.addAll(newBookmarks)
        favouriteTitles.clear()
        favouriteTitles.addAll(newFavourites)
        notifyDataSetChanged()
    }
}