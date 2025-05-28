package com.moviecommobile.presentation.screens.movie_detials_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moviecommobile.R
import com.moviecommobile.presentation.screens.movie_detials_screen.components.BlurredImageBackground
import com.moviecommobile.presentation.screens.movie_detials_screen.components.ChipSize
import com.moviecommobile.presentation.screens.movie_detials_screen.components.MovieChip
import com.moviecommobile.presentation.screens.movie_detials_screen.components.TitledContent
import kotlin.math.round


@Composable
fun MovieDetailsScreenRoot(
    viewModel:  MovieDetailsViewModel,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MovieDetailsScreen(
        state = state,
        onAction = { action ->
            when(action) {
                is MovieDetailsAction.OnBackClick -> onBackClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun MovieDetailsScreen(
    state: MovieDetailsState,
    onAction: (MovieDetailsAction) -> Unit
) {
    BlurredImageBackground(
        imageUrl = state.movie?.poster,
        isFavorite = state.isFavorite,
        onFavoriteClick = {
            onAction(MovieDetailsAction.OnFavoriteClick)
        },
        onBackClick = {
            onAction(MovieDetailsAction.OnBackClick)
        },
        modifier = Modifier.fillMaxSize()
    ) {
        if (state.movie != null) {
            Column(
                modifier = Modifier
                    .widthIn(max = 700.dp)
                    .fillMaxWidth()
                    .padding(
                        vertical = 16.dp,
                        horizontal = 24.dp
                    )
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = state.movie.title,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = state.movie.year.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    state.movie.imdb?.rating?.let {
                        TitledContent(
                            title = stringResource(R.string.rating)
                        ) {
                            MovieChip {
                                Row {
                                    Text(
                                        text = (round(it * 10) / 10).toString(),
                                        color = Color.White
                                    )
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        tint = Color.Yellow
                                    )
                                }
                            }
                        }
                    }
                    state.movie.runtime?.let {
                        TitledContent(
                            title = stringResource(R.string.runtime)
                        ) {
                            MovieChip {
                                Text(
                                    text = "$it min",
                                    color = Color.White
                                )
                            }
                        }
                    }

                }
                if (state.movie.languages.isNotEmpty()) {
                    TitledContent(
                        title = stringResource(R.string.languages),
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        FlowRow(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.wrapContentSize(Alignment.Center)
                        ){
                            state.movie.languages.forEach {
                                MovieChip(
                                    size = ChipSize.SMALL,
                                    modifier = Modifier.padding(end = 2.dp)
                                ) {
                                    Text(
                                        text = it,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
                if (state.movie.genres!!.isNotEmpty()) {
                    TitledContent(
                        title = stringResource(R.string.genres),
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        FlowRow(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.wrapContentSize(Alignment.Center)
                        ){
                            state.movie.genres!!.forEach {
                                MovieChip(
                                    size = ChipSize.SMALL,
                                    modifier = Modifier.padding(end = 2.dp)
                                ) {
                                    Text(
                                        text = it,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
                Text(
                    text = stringResource(R.string.plot),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .fillMaxWidth()
                        .padding(
                            top = 24.dp,
                            bottom = 8.dp
                        )
                )
                Text(
                    text = if (state.movie.fullPlot!!.isNotEmpty()) {
                        state.movie.fullPlot!!
                    } else {
                        stringResource(R.string.not_plot)
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}