package com.moviecommobile.domain

import java.time.LocalDate

data class Movie(
    var id: String,
    var plot: String?,
    var genres: List<String>?,
    var runtime: Int? = null,
    var cast: List<String> = mutableListOf(),
    var numMflixComments: Int? = null,
    var poster: String?,
    var title: String,
    var fullPlot: String = "",
    var languages: List<String> = mutableListOf(),
    var released: LocalDate?,
    var directors: List<String> = mutableListOf(),
    var writers: List<String> = mutableListOf(),
    var awards: Awards?,
    var lastUpdated: LocalDate? = null,
    var year: Int? = null,
    var imdb: Imdb? = null,
    var countries: List<String> = mutableListOf(),
    var type: String?,
    var tomatoes: Tomatoes? = null
) {
    data class Awards(
        var wins: Int? = 0,
        var nominations: Int? = 0,
        var text: String? = ""
    )

    data class Imdb(
        var rating: Double? = 0.0,
        var votes: Int? = 0,
        var id: Int? = 0
    )

    data class Tomatoes(
        var viewer: Viewer? = Viewer(),
        var website: String? = "",
        var production: String? = "",
        var lastUpdated: LocalDate? = LocalDate.now(),
        var dvd: LocalDate? = LocalDate.now()
    ) {
        data class Viewer(
            var rating: Double? = 0.0,
            var numReviews: Double? = 0.0,
            var meter: Int? = 0
        )
    }
}