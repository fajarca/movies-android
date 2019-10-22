package io.fajarca.movies.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "casts",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = Movie::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("movie_id")
        )
    )
)
data class Cast(
    @ColumnInfo(name = "movie_id")
    var movieId: Long = 0,
    @ColumnInfo(name = "cast_id")
    var castId: Long = 0,
    @ColumnInfo(name = "character")
    var character: String = "",
    @ColumnInfo(name = "credit_id")
    var creditId: String = "",
    @ColumnInfo(name = "name")
    var name: String = "",
    @ColumnInfo(name = "profile_path")
    var profilePath: String = ""
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0
}
