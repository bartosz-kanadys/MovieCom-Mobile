package com.moviecommobile.domain

import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun searchMovies(query: String): Result<List<Movie>, DataError.Remote>

    fun getFavoriteMovies(): Flow<List<Movie>>
    fun isMovieFavorite(id: String): Flow<Boolean>
    suspend fun markAsFavorite(movie: Movie): EmptyResult<DataError.Local>
    suspend fun unmarkAsFavorite(id: String)

}