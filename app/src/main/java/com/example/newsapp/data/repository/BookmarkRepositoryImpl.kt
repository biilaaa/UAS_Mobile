package com.example.newsapp.data.repository

import com.example.newsapp.data.db.bookmark.BookmarkDao
import com.example.newsapp.data.db.bookmark.BookmarkEntity
import com.example.newsapp.domain.repository.BookmarkRepository

class BookmarkRepositoryImpl(
    private val dao: BookmarkDao
) : BookmarkRepository {

    override suspend fun getBookmarks(): List<BookmarkEntity> {
        return dao.getAllBookmarks()
    }

    override suspend fun addBookmark(bookmark: BookmarkEntity) {
        dao.insertBookmark(bookmark)
    }

    override suspend fun removeBookmark(bookmark: BookmarkEntity) {
        dao.deleteBookmark(bookmark)
    }
}