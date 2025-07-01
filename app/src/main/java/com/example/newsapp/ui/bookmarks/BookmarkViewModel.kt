package com.example.newsapp.ui.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.db.bookmark.BookmarkEntity
import com.example.newsapp.domain.usecase.BookmarkUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookmarkViewModel(private val useCase: BookmarkUseCase) : ViewModel() {

    private val _bookmarks = MutableStateFlow<List<BookmarkEntity>>(emptyList())
    val bookmarks: StateFlow<List<BookmarkEntity>> = _bookmarks

    fun loadBookmarks() {
        viewModelScope.launch {
            _bookmarks.value = useCase.getBookmarks()
        }
    }

    fun addBookmark(bookmark: BookmarkEntity) {
        viewModelScope.launch {
            useCase.addBookmark(bookmark)
            loadBookmarks()
        }
    }

    fun removeBookmark(bookmark: BookmarkEntity) {
        viewModelScope.launch {
            useCase.removeBookmark(bookmark)
            loadBookmarks()
        }
    }
}