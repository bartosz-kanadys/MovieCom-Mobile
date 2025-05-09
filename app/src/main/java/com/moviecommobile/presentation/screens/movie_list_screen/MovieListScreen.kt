package com.moviecommobile.presentation.screens.movie_list_screen

import com.moviecommobile.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.compose.AppTheme
import com.moviecommobile.domain.Movie
import com.moviecommobile.presentation.screens.movie_list_screen.composables.MovieList
import com.moviecommobile.presentation.screens.movie_list_screen.composables.MovieListItem
import com.moviecommobile.presentation.screens.movie_list_screen.composables.MovieSearchBar
import com.moviecommobile.presentation.theme.UiText
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MovieListScreenRoot(
    viewModel: MovieListViewModel = koinViewModel(),
    onMovieClick: (Movie) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MovieListScreen(
        state = state,
        onAction = { action ->
            when(action) {
                is MovieListAction.OnMovieClick -> onMovieClick(action.movie)
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun MovieListScreen(
    state: MovieListState,
    onAction: (MovieListAction) -> Unit
)
{
    val pagerState = rememberPagerState { 2 }
    val keyboardController = LocalSoftwareKeyboardController.current
    val searchResultListState= rememberLazyListState()

    //After new search animate to first result on list
    LaunchedEffect(state.searchResult) {
        searchResultListState.animateScrollToItem(0)
    }

    //After click on tab we changing pager state
    LaunchedEffect(state.selectedTabIndex) {
        pagerState.animateScrollToPage(state.selectedTabIndex)
    }

    //After change page we changing selected tab index
    LaunchedEffect(pagerState.currentPage) {
        onAction(MovieListAction.OnTabSelected(pagerState.currentPage))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        MovieSearchBar(
            searchQuery = state.searchQuery,
            onSearchQueryChange = {
                onAction(MovieListAction.OnSearchQueryChange(it))
            },
            onImeSearch = {
                keyboardController?.hide()
            },
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth()
                .padding(16.dp)
        )

        Surface(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TabRow(
                    selectedTabIndex = state.selectedTabIndex,
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .widthIn(max = 700.dp)
                        .fillMaxWidth()
                ) {
                    Tab(
                        selected = state.selectedTabIndex == 0,
                        onClick = {
                            onAction(MovieListAction.OnTabSelected(0))
                        },
                        modifier = Modifier.weight(1f),
                        selectedContentColor = MaterialTheme.colorScheme.primary,
                        unselectedContentColor = Color.Black.copy(alpha = 0.5f)
                    ) {
                        Text(
                            text = UiText.StringResourceId(R.string.searched_movies).asString(),
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                    Tab(
                        selected = state.selectedTabIndex == 1,
                        onClick = {
                            onAction(MovieListAction.OnTabSelected(1))
                        },
                        modifier = Modifier.weight(1f),
                        selectedContentColor = MaterialTheme.colorScheme.primary,
                        unselectedContentColor = Color.Black.copy(alpha = 0.5f)
                    ) {
                        Text(
                            text = UiText.StringResourceId(R.string.favorites).asString(),
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) { pageIndex ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.TopCenter
                    ){
                        when (pageIndex) {
                            0 -> {
                                if (state.isLoading) {
                                    CircularProgressIndicator()
                                } else {
                                    when {
                                        state.errorMessage != null -> {
                                            Text(
                                                text = state.errorMessage.asString(),
                                                textAlign = TextAlign.Center,
                                                style = MaterialTheme.typography.titleLarge,
                                                color = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier
                                                    .padding(vertical = 100.dp)
                                            )
                                        }
                                        state.searchResult.isEmpty() -> {
                                            Text(
                                                text = stringResource(R.string.not_found),
                                                textAlign = TextAlign.Center,
                                                style = MaterialTheme.typography.titleLarge,
                                                color = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier
                                                    .padding(vertical = 100.dp)
                                            )
                                        }
                                        else -> {
                                            MovieList(
                                                movies = state.searchResult,
                                                onMovieClick = {
                                                    onAction(MovieListAction.OnMovieClick(it))
                                                },
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                scrollState = searchResultListState
                                            )
                                        }
                                    }
                                }
                            }
                            1 -> {
                                if (state.favoriteMovies.isEmpty()) {
                                    Text(
                                        text = stringResource(R.string.favorites_not_found),
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.titleLarge,
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier
                                            .padding(vertical = 100.dp)
                                    )
                                } else {
                                    MovieList(
                                        movies = state.favoriteMovies,
                                        onMovieClick = {
                                            onAction(MovieListAction.OnMovieClick(it))
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        scrollState = searchResultListState
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MovieListScreenPreview() {
    AppTheme {
        MovieListScreenRoot(){}
    }
}