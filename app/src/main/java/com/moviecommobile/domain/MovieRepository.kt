package com.moviecommobile.domain

interface MovieRepository {
    suspend fun searchMovies(query: String): Result<List<Movie>, DataError.Remote>
}