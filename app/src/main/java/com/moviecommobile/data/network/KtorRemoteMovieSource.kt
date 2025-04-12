package com.moviecommobile.data.network

import com.moviecommobile.data.dto.SearchResponseDto
import com.moviecommobile.data.safeCall
import com.moviecommobile.domain.DataError
import com.moviecommobile.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val BASE_URL = "http://192.168.0.108:9000"

class KtorRemoteMovieSource(
    private val httpClient: HttpClient
): RemoteMovieDataSource {
    override suspend fun searchMovies(
        query: String,
        resultLimit: Int?
    ): Result<SearchResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "$BASE_URL/movies/title/$query"
            )
        }
    }
}