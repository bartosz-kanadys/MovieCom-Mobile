package com.moviecommobile.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.moviecommobile.domain.Movie.Imdb
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Entity
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)var id: String,
    var plot: String?,
    var genres: List<String>?,
    var runtime: Int?,
    var cast: List<String>,
    var poster: String?,
    var title: String,
    var fullPlot: String?,
    var languages: List<String>,
    var released: LocalDate?,
    var directors: List<String>,
    var writers: List<String>,
    var year: Int?,
    var imdb: Imdb?,
    var countries: List<String>,
)

