package com.moviecommobile.core.di

import com.moviecommobile.data.database.DatabaseFactory
import com.moviecommobile.data.database.FavoriteMovieDao
import com.moviecommobile.data.database.FavoriteMovieDatabase
import com.moviecommobile.data.network.HttpClientFactory
import com.moviecommobile.data.network.KtorRemoteMovieSource
import com.moviecommobile.data.network.RemoteMovieDataSource
import com.moviecommobile.data.repository.DefaultMovieRepository
import com.moviecommobile.domain.MovieRepository
import com.moviecommobile.presentation.screens.SelectedMovieViewModel
import com.moviecommobile.presentation.screens.movie_detials_screen.MovieDetailsViewModel
import com.moviecommobile.presentation.screens.movie_list_screen.MovieListViewModel
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    // Network setup
    single { HttpClientFactory.create(OkHttp.create()) }
    singleOf(::KtorRemoteMovieSource).bind<RemoteMovieDataSource>()


    // Database setup
    single { DatabaseFactory(get()).create() }
    single<FavoriteMovieDao> { get<FavoriteMovieDatabase>().movieDao() }

    // Repository
    singleOf(::DefaultMovieRepository).bind<MovieRepository>()

    // ViewModels
    viewModelOf(::MovieListViewModel)
    viewModelOf(::MovieDetailsViewModel)
    viewModelOf(::SelectedMovieViewModel)
}