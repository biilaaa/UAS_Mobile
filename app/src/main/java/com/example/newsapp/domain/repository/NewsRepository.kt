package com.example.newsapp.domain.repository

import com.example.newsapp.data.api.NewsApiService
import com.example.newsapp.domain.model.ArticleDomain
import com.example.newsapp.domain.model.NewsDomain
import com.example.newsapp.domain.repository.NewsRepository

class NewsRepositoryImpl(private val api: NewsApiService) : NewsRepository {

    override suspend fun getTopHeadlines(apiKey: String): NewsDomain {
        val response = api.getTopHeadlines(apiKey = apiKey)
        val articles = response.articles.map {
            ArticleDomain(
                title = it.title,
                description = it.description,
                urlToImage = it.urlToImage
            )
        }
        return NewsDomain(articles)
    }
}

interface NewsRepository {
    suspend fun getTopHeadlines(apiKey: String): NewsDomain
}