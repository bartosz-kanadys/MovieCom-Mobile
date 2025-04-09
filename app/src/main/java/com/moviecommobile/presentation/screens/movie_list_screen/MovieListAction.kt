package com.moviecommobile.presentation.screens.movie_list_screen

import com.moviecommobile.domain.Movie

sealed interface MovieListAction {
    data class OnSearchQueryChange(val query: String): MovieListAction
    data class OnMovieClick(val movie: Movie): MovieListAction
    data class OnTabSelected(val index: Int): MovieListAction
}