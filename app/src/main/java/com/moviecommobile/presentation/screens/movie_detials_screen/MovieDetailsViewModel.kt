package com.moviecommobile.presentation.screens.movie_detials_screen


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.moviecommobile.core.app.Route
import com.moviecommobile.domain.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val movieRepository: MovieRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val movieId = savedStateHandle.toRoute<Route.DetailScreen>().id


    private val _state = MutableStateFlow(MovieDetailsState())
    val state = _state
        .onStart {
            observeFavoriteStatus()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    private fun observeFavoriteStatus() {
        movieRepository
            .isMovieFavorite(movieId)
            .onEach { isFavorite ->
                _state.update { it.copy(isFavorite = isFavorite) }
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: MovieDetailsAction){
        when(action) {
            is MovieDetailsAction.OnSelectedMovieChange -> {
                _state.update { it.copy(movie = action.movie) }
            }

            is MovieDetailsAction.OnFavoriteClick -> {
                viewModelScope.launch {
                    if (state.value.isFavorite) {
                        movieRepository.unmarkAsFavorite(movieId)
                    } else {
                        movieRepository.markAsFavorite(state.value.movie!!)
                    }
                }
            }
            else -> Unit
        }
    }
}