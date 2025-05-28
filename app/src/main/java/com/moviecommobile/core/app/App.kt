package com.moviecommobile.core.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.compose.AppTheme
import com.moviecommobile.presentation.screens.SelectedMovieViewModel
import com.moviecommobile.presentation.screens.movie_detials_screen.MovieDetailsAction
import com.moviecommobile.presentation.screens.movie_detials_screen.MovieDetailsScreenRoot
import com.moviecommobile.presentation.screens.movie_detials_screen.MovieDetailsViewModel
import com.moviecommobile.presentation.screens.movie_list_screen.MovieListScreenRoot
import com.moviecommobile.presentation.screens.movie_list_screen.MovieListViewModel
import org.koin.compose.viewmodel.koinViewModel


@Composable
@Preview
fun App() {
    AppTheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Route.MovieGraph
        ) {
            navigation<Route.MovieGraph>(
                startDestination = Route.MovieList
            ) {
                composable<Route.MovieList> {
                    val viewModel = koinViewModel<MovieListViewModel>()
                    val sharedViewModel = it.sharedKoinViewModel<SelectedMovieViewModel>(navController)

                    LaunchedEffect(true) {
                        sharedViewModel.onSelectMovie(null)
                    }

                    MovieListScreenRoot(
                         viewModel = viewModel,
                         onMovieClick = {
                             sharedViewModel.onSelectMovie(it)
                             navController.navigate(Route.DetailScreen(it.id))
                         }
                     )
                }
                composable<Route.DetailScreen> {
                    val sharedViewModel = it.sharedKoinViewModel<SelectedMovieViewModel>(navController)
                    val viewModel = koinViewModel<MovieDetailsViewModel>()
                    val selectedMovie by sharedViewModel.selectedMovie.collectAsStateWithLifecycle()

                    LaunchedEffect(selectedMovie) {
                        selectedMovie?.let {
                            viewModel.onAction(MovieDetailsAction.OnSelectedMovieChange(it))

                        }
                    }

                    MovieDetailsScreenRoot(
                        viewModel = viewModel,
                        onBackClick = {
                            navController.navigateUp()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private inline fun <reified T: ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavController
): T {
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return koinViewModel(
        viewModelStoreOwner = parentEntry
    )
}