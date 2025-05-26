package com.moviecommobile.domain.di

import com.moviecommobile.data.network.HttpClientFactory
import com.moviecommobile.data.network.KtorRemoteMovieSource
import com.moviecommobile.data.network.RemoteMovieDataSource
import com.moviecommobile.data.repository.DefaultMovieRepository
import com.moviecommobile.domain.MovieRepository
import com.moviecommobile.presentation.screens.movie_list_screen.MovieListViewModel
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(OkHttp.create()) }
    singleOf(::KtorRemoteMovieSource).bind<RemoteMovieDataSource>()
    singleOf(::DefaultMovieRepository).bind<MovieRepository>()

    viewModelOf(::MovieListViewModel)
}