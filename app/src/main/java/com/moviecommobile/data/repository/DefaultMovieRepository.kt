package com.moviecommobile.data.repository

import com.moviecommobile.data.mappers.toMovie
import com.moviecommobile.data.network.RemoteMovieDataSource
import com.moviecommobile.domain.DataError
import com.moviecommobile.domain.Movie
import com.moviecommobile.domain.MovieRepository
import com.moviecommobile.domain.Result
import com.moviecommobile.domain.map

class DefaultMovieRepository(
    private val remoteMovieDataSource: RemoteMovieDataSource
): MovieRepository {
    override suspend fun searchMovies(query: String): Result<List<Movie>, DataError.Remote> {
        return remoteMovieDataSource
            .searchMovies(query)
            .map { dto ->
                dto.map {it.toMovie()}
            }
    }
}