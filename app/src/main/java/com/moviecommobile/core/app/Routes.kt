package com.moviecommobile.core.app

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    @Serializable
    data object MovieGraph : Route

    @Serializable
    data object MovieList : Route

    @Serializable
    data class DetailScreen(val id: String) : Route
}