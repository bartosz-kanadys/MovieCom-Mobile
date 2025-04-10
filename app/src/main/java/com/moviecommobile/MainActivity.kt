package com.moviecommobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.AppTheme
import com.moviecommobile.presentation.screens.movie_list_screen.MovieListScreenRoot
import com.moviecommobile.presentation.screens.movie_list_screen.MovieListViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                MovieListScreenRoot(
                    viewModel = remember { MovieListViewModel() },
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