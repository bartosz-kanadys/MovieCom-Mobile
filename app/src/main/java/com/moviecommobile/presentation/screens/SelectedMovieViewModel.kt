package com.moviecommobile.presentation.screens

import androidx.lifecycle.ViewModel
import com.moviecommobile.domain.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SelectedMovieViewModel : ViewModel() {
    private val _selectedMovie = MutableStateFlow<Movie?>(null)
    val selectedMovie = _selectedMovie.asStateFlow()

    fun onSelectMovie(movie: Movie?) {
        _selectedMovie.value = movie
    }
}