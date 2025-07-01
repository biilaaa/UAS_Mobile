package com.example.newsapp.ui.bookmarks

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentBookmarksBinding
import com.example.newsapp.domain.model.ArticleDomain
import com.example.newsapp.ui.adapter.NewsAdapter
import com.example.newsapp.ui.detail.DetailActivity
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class BookmarksFragment : Fragment(R.layout.fragment_bookmarks) {

    private var _binding: FragmentBookmarksBinding? = null
    private val binding get() = _binding!!
    private val bookmarkDao by inject<com.example.newsapp.data.db.bookmark.BookmarkDao>()
    private lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentBookmarksBinding.bind(view)

        setupRecyclerView()
        loadBookmarks()
    }
    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter(
            emptyList(),
            bookmarkedTitles = emptyList(),
            favouriteTitles = emptyList(),
            onItemClick = { article ->
                val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                    putExtra("title", article.title)
                    putExtra("description", article.description)
                    putExtra("image", article.urlToImage)
                }
                startActivity(intent)
            },
            onBookmarkClick = { article ->
                removeFromBookmark(article)
            },
            onFavouriteClick = { }
        )

        binding.recyclerViewBookmarks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newsAdapter
        }
    }

    private fun loadBookmarks() {
        lifecycleScope.launch {
            val bookmarks = bookmarkDao.getAllBookmarks().map {
                ArticleDomain(it.title, it.description, it.urlToImage)
            }

            binding.emptyText.visibility = if (bookmarks.isEmpty()) View.VISIBLE else View.GONE
            newsAdapter.setData(bookmarks, bookmarks.map { it.title }, emptyList())
        }
    }

    private fun removeFromBookmark(article: ArticleDomain) {
        lifecycleScope.launch {
            bookmarkDao.deleteBookmarkByTitle(article.title)
            loadBookmarks()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}