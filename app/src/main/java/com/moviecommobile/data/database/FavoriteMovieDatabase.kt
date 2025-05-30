package com.moviecommobile.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.moviecommobile.data.database.converters.ImdbConverter
import com.moviecommobile.data.database.converters.LocalTimeConverter
import com.moviecommobile.data.database.converters.StringListTypeConverter

@Database(
    entities = [MovieEntity::class],
    version = 1
)
@TypeConverters(
    StringListTypeConverter::class,
    LocalTimeConverter::class,
    ImdbConverter::class
)
abstract class FavoriteMovieDatabase: RoomDatabase() {
    abstract fun movieDao(): FavoriteMovieDao
}