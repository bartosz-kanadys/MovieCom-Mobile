package com.moviecommobile.data.database.converters

import androidx.room.TypeConverter
import com.moviecommobile.domain.Movie.Imdb
import kotlinx.serialization.json.Json

object ImdbConverter {
    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromString(value: String?): Imdb? {
        return value?.let { json.decodeFromString<Imdb>(it) }
    }

    @TypeConverter
    fun toString(imdb: Imdb?): String? {
        return imdb?.let { json.encodeToString(it) }
    }
}