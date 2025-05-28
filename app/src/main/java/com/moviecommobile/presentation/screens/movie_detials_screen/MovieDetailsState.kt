package com.moviecommobile.presentation.screens.movie_detials_screen

import com.moviecommobile.domain.Movie

data class MovieDetailsState(
    val isLoading: Boolean = true,
    val isFavorite: Boolean = false,
    val movie: Movie? = null
)