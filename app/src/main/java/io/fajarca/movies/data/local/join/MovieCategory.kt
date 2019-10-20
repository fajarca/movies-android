package io.fajarca.movies.data.local.join

import androidx.room.ColumnInfo

data class MovieCategory(

    @ColumnInfo(name = "movie_id")
    val movieId: Long,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "overview")
    val overview: String,

    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String,

    @ColumnInfo(name = "poster_path")
    val posterPath: String,

    @ColumnInfo(name = "video")
    val video: Boolean,

    @ColumnInfo(name = "adult")
    val adult: Boolean,

    @ColumnInfo(name = "release_date")
    val releaseDate: String,

    @ColumnInfo(name = "runtime")
    val runtime: String,

    @ColumnInfo(name = "vote_count")
    var voteCount: Long = 0,

    @ColumnInfo(name = "vote_average")
    var voteAverage: Float = 0.0f,

    @ColumnInfo(name = "category_id")
    val categoryId: Long,

    @ColumnInfo(name = "category_name")
    val categoryName: String
)