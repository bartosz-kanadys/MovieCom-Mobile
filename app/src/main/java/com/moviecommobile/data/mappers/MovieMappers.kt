package com.moviecommobile.data.mappers

import com.moviecommobile.data.dto.SearchedMovieDto
import com.moviecommobile.domain.Movie
import java.time.LocalDate
import java.time.format.DateTimeParseException

fun SearchedMovieDto.toMovie(): Movie {
    return Movie(
        id = id,
        plot = plot,
        genres = genres,
        runtime = runtime,
        cast = cast,
        poster = poster,
        title = title,
        fullPlot = fullPlot,
        languages = languages,
        released = released?.let {
            try {
                LocalDate.parse(it)
            } catch (e: DateTimeParseException) {
                null
            }
        },
        directors = directors,
        writers = writers,
        year = year,
        imdb = imdb?.let {
            Movie.Imdb(
                rating = it.rating,
                votes = it.votes,
            )
        },
        countries = countries
    )
}