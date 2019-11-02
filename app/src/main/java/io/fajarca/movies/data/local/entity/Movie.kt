package io.fajarca.movies.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Long = 0,

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "original_title")
    var originalTitle: String = "",

    @ColumnInfo(name = "overview")
    var overview: String = "",

    @ColumnInfo(name = "backdrop_path")
    var backdropPath: String? = "",

    @ColumnInfo(name = "poster_path")
    var posterPath: String = "",

    @ColumnInfo(name = "original_language")
    var originalLanguage: String = "",

    @ColumnInfo(name = "popularity")
    var popularity: Float = 0.0f,

    @ColumnInfo(name = "vote_count")
    var voteCount: Long = 0,

    @ColumnInfo(name = "vote_average")
    var voteAverage: Float = 0.0f,

    @ColumnInfo(name = "video")
    var video: Boolean = false,

    @ColumnInfo(name = "adult")
    var adult: Boolean = false,

    @ColumnInfo(name = "release_date")
    var releaseDate: String = "",

    @ColumnInfo(name = "runtime")
    var runtime: Int = 0
)
