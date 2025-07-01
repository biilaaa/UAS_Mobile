package com.example.newsapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsapp.data.db.Favourite.FavouriteDao
import com.example.newsapp.data.db.Favourite.FavouriteEntity
import com.example.newsapp.data.db.bookmark.BookmarkDao
import com.example.newsapp.data.db.bookmark.BookmarkEntity

@Database(
    entities = [ArticleEntity::class, BookmarkEntity::class, FavouriteEntity::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun bookmarkDao(): BookmarkDao
    abstract fun favouriteDao(): FavouriteDao
}