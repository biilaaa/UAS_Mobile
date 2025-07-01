package com.example.newsapp.data.db.Favourite

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavouriteDao {

    @Query("SELECT * FROM favourites")
    suspend fun getAllFavourites(): List<FavouriteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourite(favourite: FavouriteEntity)

    @Query("DELETE FROM favourites WHERE title = :title")
    suspend fun deleteFavourite(title: String)
}
