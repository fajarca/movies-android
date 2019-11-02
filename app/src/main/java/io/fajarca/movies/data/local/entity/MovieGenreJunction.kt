package io.fajarca.movies.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "movie_genre_junction",
    primaryKeys = arrayOf("movie_id", "genre_id")
)
data class MovieGenreJunction(
    @ColumnInfo(name = "movie_id")
    var movieId: Long = 0,

    @ColumnInfo(name = "genre_id")
    var categoryId: Long = 0
)
