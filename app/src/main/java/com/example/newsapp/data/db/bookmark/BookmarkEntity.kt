package com.example.newsapp.data.db.bookmark

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey val title: String,
    val description: String?,
    val urlToImage: String?
)
