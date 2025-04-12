package com.moviecommobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.AppTheme
import com.moviecommobile.data.network.HttpClientFactory
import com.moviecommobile.data.network.KtorRemoteMovieSource
import com.moviecommobile.data.repository.DefaultMovieRepository
import com.moviecommobile.domain.MovieRepository
import com.moviecommobile.presentation.screens.movie_list_screen.MovieListScreenRoot
import com.moviecommobile.presentation.screens.movie_list_screen.MovieListViewModel
import io.ktor.client.engine.okhttp.OkHttp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                MovieListScreenRoot(
                    viewModel = remember { MovieListViewModel(
                        movieRepository = DefaultMovieRepository(
                            remoteMovieDataSource = KtorRemoteMovieSource(
                                httpClient = HttpClientFactory.create(OkHttp.create())
                            )
                        )
                    ) },
                    onMovieClick = {  }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppTheme {

    }
}