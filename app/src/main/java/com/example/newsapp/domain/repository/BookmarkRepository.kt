package com.example.newsapp.domain.repository

import com.example.newsapp.data.db.bookmark.BookmarkEntity

interface BookmarkRepository {
    suspend fun getBookmarks(): List<BookmarkEntity>
    suspend fun addBookmark(bookmark: BookmarkEntity)
    suspend fun removeBookmark(bookmark: BookmarkEntity)
}
