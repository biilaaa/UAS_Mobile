package com.example.newsapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.usecase.GetTopHeadlinesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase) : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state

    fun loadNews(apiKey: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val news = getTopHeadlinesUseCase(apiKey)
                _state.value = MainState(articles = news.articles)
            } catch (e: Exception) {
                _state.value = MainState(error = e.message)
            }
        }
    }
}