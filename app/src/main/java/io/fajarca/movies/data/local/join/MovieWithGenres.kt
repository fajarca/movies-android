package io.fajarca.movies.data.local.join

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import io.fajarca.movies.data.local.entity.Genre
import io.fajarca.movies.data.local.entity.Movie
import io.fajarca.movies.data.local.entity.MovieGenreJunction

data class MovieWithGenres(
    @Embedded
    var movie: Movie,
    @Relation(
        entity = Genre::class,
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            MovieGenreJunction::class,
            parentColumn = "movie_id",
            entityColumn = "genre_id"
        )
    )
    var genres: List<Genre>
)