package com.example.newsapp.ui.favourite

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentFavouriteBinding
import com.example.newsapp.domain.model.ArticleDomain
import com.example.newsapp.ui.adapter.NewsAdapter
import com.example.newsapp.ui.detail.DetailActivity
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class FavouriteFragment : Fragment(R.layout.fragment_favourite) {

    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding!!

    private val favouriteDao by inject<com.example.newsapp.data.db.Favourite.FavouriteDao>()
    private lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentFavouriteBinding.bind(view)

        setupRecyclerView()
        loadFavourites()
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
            onBookmarkClick = { },
            onFavouriteClick = { article ->
                removeFromFavourite(article)
            }
        )

        binding.recyclerViewFavourite.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newsAdapter
        }
    }

    private fun loadFavourites() {
        lifecycleScope.launch {
            val favourites = favouriteDao.getAllFavourites().map {
                ArticleDomain(it.title, it.description, it.urlToImage)
            }

            binding.emptyTextFavourite.visibility = if (favourites.isEmpty()) View.VISIBLE else View.GONE
            newsAdapter.setData(favourites, emptyList(), favourites.map { it.title })
        }
    }

    private fun removeFromFavourite(article: ArticleDomain) {
        lifecycleScope.launch {
            favouriteDao.deleteFavourite(article.title)
            Toast.makeText(requireContext(), "Dihapus dari Favourite", Toast.LENGTH_SHORT).show()
            loadFavourites()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}