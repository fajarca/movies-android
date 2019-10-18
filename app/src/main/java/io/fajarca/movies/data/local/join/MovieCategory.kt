package io.fajarca.movies.data.local.join

import androidx.room.ColumnInfo
import androidx.room.Embedded
import io.fajarca.movies.data.local.entity.Category
import io.fajarca.movies.data.local.entity.Movie

data class MovieCategory(

    @ColumnInfo(name = "movie_id")
    val movieId : Long,

    @ColumnInfo(name = "title")
    val title : String,

    @ColumnInfo(name = "overview")
    val overview : String,

    @ColumnInfo(name = "backdrop_path")
    val backdropPath : String,

    @ColumnInfo(name = "poster_path")
    val posterPath : String,

    @ColumnInfo(name = "video")
    val video : Boolean,

    @ColumnInfo(name = "adult")
    val adult : Boolean,

    @ColumnInfo(name = "release_date")
    val releaseDate : String,

    @ColumnInfo(name = "category_id")
    val categoryId : Long,

    @ColumnInfo(name = "category_name")
    val categoryName : String
)