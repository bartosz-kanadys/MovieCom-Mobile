package com.moviecommobile.data.network

import com.moviecommobile.data.dto.SearchResponseDto
import com.moviecommobile.domain.DataError
import com.moviecommobile.domain.Result

interface RemoteMovieDataSource {
    suspend fun searchMovies(
        query: String,
        resultLimit: Int? = null
    ): Result<SearchResponseDto, DataError.Remote>
}