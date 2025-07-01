package com.example.newsapp.data.db.Favourite

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites")
data class FavouriteEntity(
    @PrimaryKey val title: String,
    val description: String?,
    val urlToImage: String?
)
