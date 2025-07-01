package com.example.newsapp.data.repository

import com.example.newsapp.data.api.NewsApiService
import com.example.newsapp.data.db.ArticleEntity
import com.example.newsapp.domain.model.ArticleDomain
import com.example.newsapp.domain.model.NewsDomain
import com.example.newsapp.domain.repository.NewsRepository
import com.example.newsapp.data.db.ArticleDao

class NewsRepositoryImpl(
    private val api: NewsApiService,
    private val dao: ArticleDao
) : NewsRepository {

    override suspend fun getTopHeadlines(apiKey: String): NewsDomain {
        return try {
            val response = api.getTopHeadlines(apiKey = apiKey)
            val articles = response.articles.map {
                ArticleDomain(
                    title = it.title.orEmpty(),
                    description = it.description.orEmpty(),
                    urlToImage = it.urlToImage.orEmpty()
                )
            }
            // Simpan ke Room
            dao.clearArticles()
            dao.insertArticles(articles.map { ArticleEntity(it.title, it.description, it.urlToImage) })
            NewsDomain(articles)
        } catch (e: Exception) {
            // Ambil dari Room  error (misal offline)
            val localArticles = dao.getAllArticles().map {
                ArticleDomain(it.title, it.description, it.urlToImage)
            }
            NewsDomain(localArticles)
        }
    }
}
