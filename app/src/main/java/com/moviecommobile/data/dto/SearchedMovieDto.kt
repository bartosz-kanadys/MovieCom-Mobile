package com.moviecommobile.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchedMovieDto(
    @SerialName("_id") val id: String,
    @SerialName("plot") val plot: String?,
    @SerialName("genres") val genres: List<String>?,
    @SerialName("runtime") val runtime: Int? = null,
    @SerialName("cast") val cast: List<String> = mutableListOf(),
    @SerialName("poster") val poster: String?,
    @SerialName("title") val title: String,
    @SerialName("fullplot") val fullPlot: String,
    @SerialName("languages") val languages: List<String> = mutableListOf(),
    @SerialName("released") val released: String?,
    @SerialName("directors") val directors: List<String> = mutableListOf(),
    @SerialName("writers") val writers: List<String> = mutableListOf(),
    @SerialName("year") val year: Int? = null,
    @SerialName("imdb") val imdb: Imdb? = null,
    @SerialName("countries")val countries: List<String> = mutableListOf(),

    ) {
    @Serializable
    data class Imdb(
        @SerialName("rating") val rating: Double? = 0.0,
        @SerialName("votes") val votes: Int? = 0,
    )
}
