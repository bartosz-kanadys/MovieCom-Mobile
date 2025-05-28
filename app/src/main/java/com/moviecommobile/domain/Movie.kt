package com.moviecommobile.domain

import java.time.LocalDate

data class Movie(
    var id: String,
    var plot: String?,
    var genres: List<String>?,
    var runtime: Int? = null,
    var cast: List<String> = mutableListOf(),
    var poster: String?,
    var title: String,
    var fullPlot: String?,
    var languages: List<String> = mutableListOf(),
    var released: LocalDate?,
    var directors: List<String> = mutableListOf(),
    var writers: List<String> = mutableListOf(),
    var year: Int? = null,
    var imdb: Imdb? = null,
    var countries: List<String> = mutableListOf(),
) {
    data class Imdb(
        var rating: Double? = 0.0,
        var votes: Int? = 0,
    )
}