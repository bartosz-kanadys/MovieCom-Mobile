package com.moviecommobile.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

class DatabaseFactory(
    private val context: Context
) {
    fun create(): FavoriteMovieDatabase {
        return Room.databaseBuilder(
            context = context.applicationContext,
            klass = FavoriteMovieDatabase::class.java,
            name = "favorite_movie_db"
        )
            .build()
    }
}