package com.example.newsapp.domain.usecase

import com.example.newsapp.domain.repository.NewsRepository

class GetTopHeadlinesUseCase(private val repository: NewsRepository) {
    suspend operator fun invoke(apiKey: String) = repository.getTopHeadlines(apiKey)
}
