@file:OptIn(FlowPreview::class)

package com.moviecommobile.presentation.screens.movie_list_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviecommobile.domain.Movie
import com.moviecommobile.domain.MovieRepository
import com.moviecommobile.domain.onError
import com.moviecommobile.domain.onSuccess
import com.moviecommobile.presentation.theme.toUiText
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private var cachedMovies = emptyList<Movie>()
    private var searchJob: Job? = null

    private val _state = MutableStateFlow(MovieListState())
    val state = _state
        .onStart { // To jest operator Flow, który uruchamia się, gdy ktoś po raz pierwszy subskrybuje ten flow (np. ekran UI).
            if (cachedMovies.isEmpty()) {
                observeSearchQuery()
            }
        }
        .stateIn( // przechowuje stan w viewModel
            viewModelScope, // Gdzie flow „żyje”. Jeśli ViewModel znika — flow też.
            SharingStarted.WhileSubscribed(5000L), // Flow działa, gdy ktoś go subskrybuje, i utrzymuje się jeszcze przez 5 sekund po tym.
            _state.value //Początkowa wartość — zanim flow zacznie emitować cokolwiek.
        )


    fun onAction(action: MovieListAction) {
        when (action) {
            is MovieListAction.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = action.query)
                }
            }
            is MovieListAction.OnMovieClick -> {

            }
            is MovieListAction.OnTabSelected -> {
                _state.update {
                    it.copy(selectedTabIndex = action.index)
                }
            }
        }
    }
    // live search with debounce
    private fun observeSearchQuery() {
        state
            .map { it.searchQuery } //wybiera tylko searchQuery z state
            .distinctUntilChanged() //ignorowanie takich samych wartości
            .debounce { 500L } // odczekuje 500ms przed wykonaniem działania po wpisaniu ostatniego znaku
            .onEach { query -> // dla kazdego wywołania funkcji
                when {
                    query.isBlank() -> {  // jeżeli query jest pusta, to pokaż wszystkie filmy z cache
                        _state.update {
                            it.copy(
                                errorMessage = null,
                                searchResult = cachedMovies
                            )
                        }
                    }
                    query.length >= 3 -> { // jeżeli query ma co najmniej 3 znaki, to szukaj filmow
                        searchJob?.cancel() // anulowanie poprzedniego zadania
                        searchJob = searchMovies(query) // wykonanie nowgo zadania
                    }
                }
            }
            .launchIn(viewModelScope) // uruchamia wiewModelScope
    }

    private fun searchMovies(query: String) = viewModelScope.launch { // wykonuje sie w tle
        _state.update { //jesli sie wykonuje to ustawia loading na true
            it.copy(
                isLoading = true
            )
        }
        movieRepository
            .searchMovies(query) // wykonuje zapytanie
            .onSuccess { searchResult ->    // jesli sie uda to ustawia loading na false i zapisuje wyniki
                _state.update {
                    it.copy(
                        isLoading = false,
                        searchResult = searchResult,
                        errorMessage = null
                    )
                }
            }
            .onError { error -> //jesli sie nie uda to ustawia loading na false i zapisuje blad
                _state.update {
                    it.copy(
                        isLoading = false,
                        searchResult = emptyList(),
                        errorMessage = error.toUiText()
                    )

                }
            }
    }
}