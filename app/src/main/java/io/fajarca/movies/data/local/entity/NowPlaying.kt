package io.fajarca.movies.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "now_playings")
data class NowPlaying(

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    var id: Long = 0,

    @SerializedName("title")
    @ColumnInfo(name = "title")
    var title : String = "",

    @SerializedName("original_title")
    @ColumnInfo(name = "original_title")
    var originalTitle: String = "",

    @SerializedName("overview")
    @ColumnInfo(name = "overview")
    var overview: String = "",

    @SerializedName("backdrop_path")
    @ColumnInfo(name = "backdrop_path")
    var backdropPath: String? = "",

    @SerializedName("poster_path")
    @ColumnInfo(name = "poster_path")
    var posterPath: String = "",

    @SerializedName("original_language")
    @ColumnInfo(name = "original_language")
    var originalLanguage: String = "",

    @SerializedName("popularity")
    @ColumnInfo(name = "popularity")
    var popularity: Double = 0.0,

    @SerializedName("vote_count")
    @ColumnInfo(name = "vote_count")
    var voteCount: Long = 0,

    @SerializedName("vote_average")
    @ColumnInfo(name = "vote_average")
    var voteAverage: Float = 0.0f,

    @SerializedName("video")
    @ColumnInfo(name = "video")
    var video : Boolean = false,

    @SerializedName("adult")
    @ColumnInfo(name = "adult")
    var adult : Boolean = false,

    @SerializedName("release_date")
    @ColumnInfo(name = "release_date")
    var releaseDate: String = "",

    @Ignore
    @SerializedName("genre_ids")
    var genresId : List<Int> = emptyList()

)
