package com.example.newsapp.ui.news

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.data.db.Favourite.FavouriteEntity
import com.example.newsapp.data.db.bookmark.BookmarkEntity
import com.example.newsapp.databinding.FragmentNewsBinding
import com.example.newsapp.ui.adapter.NewsAdapter
import com.example.newsapp.ui.detail.DetailActivity
import com.example.newsapp.ui.main.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.android.ext.android.inject

class NewsFragment : Fragment(R.layout.fragment_news) {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by sharedViewModel()
    private val bookmarkDao by inject<com.example.newsapp.data.db.bookmark.BookmarkDao>()
    private val favouriteDao by inject<com.example.newsapp.data.db.Favourite.FavouriteDao>()

    private lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentNewsBinding.bind(view)

        setupRecyclerView()
        observeNews()
        viewModel.loadNews("86a2c3e238294033a1ad33c646665343")
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter(
            emptyList(),
            bookmarkedTitles = mutableListOf(),
            favouriteTitles = mutableListOf(),
            onItemClick = { article ->
                val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                    putExtra("title", article.title)
                    putExtra("description", article.description)
                    putExtra("image", article.urlToImage)
                }
                startActivity(intent)
            },
            onBookmarkClick = { article ->
                saveToBookmark(article)
            },
            onFavouriteClick = { article ->
                saveToFavourite(article)
            }
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newsAdapter
        }

        lifecycleScope.launch {
            val bookmarks = bookmarkDao.getAllBookmarks().map { it.title }
            val favourites = favouriteDao.getAllFavourites().map { it.title }
            newsAdapter.setData(emptyList(), bookmarks, favourites)
        }
    }

    private fun observeNews() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collectLatest { state ->
                binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
                binding.recyclerView.visibility = if (state.articles.isNotEmpty()) View.VISIBLE else View.GONE

                val bookmarks = bookmarkDao.getAllBookmarks().map { it.title }
                val favourites = favouriteDao.getAllFavourites().map { it.title }

                newsAdapter.setData(state.articles, bookmarks, favourites)
            }
        }
    }

    private fun saveToBookmark(article: com.example.newsapp.domain.model.ArticleDomain) {
        lifecycleScope.launch {
            bookmarkDao.insertBookmark(
                BookmarkEntity(
                    title = article.title,
                    description = article.description,
                    urlToImage = article.urlToImage
                )
            )
            Toast.makeText(requireContext(), "Ditambahkan ke Bookmark", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveToFavourite(article: com.example.newsapp.domain.model.ArticleDomain) {
        lifecycleScope.launch {
            favouriteDao.insertFavourite(
                FavouriteEntity(
                    title = article.title,
                    description = article.description,
                    urlToImage = article.urlToImage
                )
            )
            Toast.makeText(requireContext(), "Ditambahkan ke Favourite", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}