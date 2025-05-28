package com.moviecommobile.presentation.screens.movie_detials_screen

import com.moviecommobile.domain.Movie

sealed interface MovieDetailsAction {
    data object OnBackClick: MovieDetailsAction
    data object OnFavoriteClick: MovieDetailsAction
    data class OnSelectedMovieChange(val movie: Movie): MovieDetailsAction
}