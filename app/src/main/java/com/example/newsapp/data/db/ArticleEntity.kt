package com.example.newsapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey val title: String,
    val description: String?,
    val urlToImage: String?
)