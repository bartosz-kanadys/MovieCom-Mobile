package com.moviecommobile.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {
    @Upsert
    suspend fun upsert(movie: MovieEntity)

    @Query("SELECT * FROM MovieEntity")
    fun getFavoriteMovies(): Flow<List<MovieEntity>>
    //każda zmiana w tabeli spowoduje automatyczne zaktualizowanie obserwatora

    @Query("SELECT * FROM MovieEntity WHERE id = :id")
    suspend fun getMovieById(id: String): MovieEntity?

    @Query("DELETE FROM MovieEntity WHERE id = :id")
    suspend fun deleteMovieById(id: String)
}