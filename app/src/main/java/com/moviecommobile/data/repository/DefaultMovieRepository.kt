package com.moviecommobile.data.repository

import androidx.sqlite.SQLiteException
import com.moviecommobile.data.database.FavoriteMovieDao
import com.moviecommobile.data.mappers.toMovie
import com.moviecommobile.data.mappers.toMovieEntity
import com.moviecommobile.data.network.RemoteMovieDataSource
import com.moviecommobile.domain.DataError
import com.moviecommobile.domain.EmptyResult
import com.moviecommobile.domain.Movie
import com.moviecommobile.domain.MovieRepository
import com.moviecommobile.domain.Result
import com.moviecommobile.domain.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultMovieRepository(
    private val remoteMovieDataSource: RemoteMovieDataSource,
    private val favoriteMovieDao: FavoriteMovieDao
): MovieRepository {
    override suspend fun searchMovies(query: String): Result<List<Movie>, DataError.Remote> {
        return remoteMovieDataSource
            .searchMovies(query)
            .map { dto ->
                dto.map {it.toMovie()}
            }
    }

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return favoriteMovieDao.getFavoriteMovies().map {
                it.map { it.toMovie() }
            }
    }

    override fun isMovieFavorite(id: String): Flow<Boolean> {
        return favoriteMovieDao.getFavoriteMovies().map {
            it.any { it.id == id }
        }
    }

    override suspend fun markAsFavorite(movie: Movie): EmptyResult<DataError.Local> {
        return try {
            favoriteMovieDao.upsert(movie.toMovieEntity())
            Result.Success(Unit)
        } catch (e: SQLiteException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun unmarkAsFavorite(id: String){
        favoriteMovieDao.deleteMovieById(id)
    }
}