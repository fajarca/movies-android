package io.fajarca.movies.model


import com.google.gson.annotations.SerializedName
import io.fajarca.movies.db.entity.Movie

data class NowPlayingResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
