package com.example.newsapp.domain.usecase

import com.example.newsapp.data.db.bookmark.BookmarkEntity
import com.example.newsapp.domain.repository.BookmarkRepository

class BookmarkUseCase(private val repository: BookmarkRepository) {

    suspend fun getBookmarks() = repository.getBookmarks()
    suspend fun addBookmark(bookmark: BookmarkEntity) = repository.addBookmark(bookmark)
    suspend fun removeBookmark(bookmark: BookmarkEntity) = repository.removeBookmark(bookmark)
}
