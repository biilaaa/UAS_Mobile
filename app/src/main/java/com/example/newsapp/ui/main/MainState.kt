package com.example.newsapp.ui.main

import com.example.newsapp.domain.model.ArticleDomain

data class MainState(
    val isLoading: Boolean = false,
    val articles: List<ArticleDomain> = emptyList(),
    val error: String? = null
)