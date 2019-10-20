package io.fajarca.movies.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "movie_category_join",
    primaryKeys = arrayOf("movie_id", "category_id"),
    foreignKeys = arrayOf(
        ForeignKey(
            entity = Movie::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("movie_id")
        ),
        ForeignKey(
            entity = Category::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("category_id")
        )
    )
)
data class MovieCategoryJoin(
    @ColumnInfo(name = "movie_id")
    var movieId: Long = 0,

    @ColumnInfo(name = "category_id")
    var categoryId: Long = 0
)
