package com.moviecommobile.presentation.screens.movie_list_screen

import com.moviecommobile.domain.Movie
import com.moviecommobile.presentation.theme.UiText

data class MovieListState(
    val searchQuery: String = "Last",
    val searchResult: List<Movie> = emptyList(),
    val favoriteMovies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val selectedTabIndex: Int = 0,
    val errorMessage: UiText? = null
)
