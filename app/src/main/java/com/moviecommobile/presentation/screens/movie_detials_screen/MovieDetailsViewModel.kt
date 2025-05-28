package com.moviecommobile.presentation.screens.movie_detials_screen


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MovieDetailsViewModel: ViewModel() {
    private val _state = MutableStateFlow(MovieDetailsState())
    val state = _state.asStateFlow()

    fun onAction(action: MovieDetailsAction){
        when(action) {
            is MovieDetailsAction.OnSelectedMovieChange -> {
                _state.update { it.copy(movie = action.movie) }
            }

            is MovieDetailsAction.OnFavoriteClick -> {

            }
            else -> Unit
        }
    }
}