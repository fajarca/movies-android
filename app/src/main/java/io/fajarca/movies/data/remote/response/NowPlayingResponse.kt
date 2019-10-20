package io.fajarca.movies.data.remote.response

import com.google.gson.annotations.SerializedName
import io.fajarca.movies.data.local.entity.NowPlaying

data class NowPlayingResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<NowPlaying>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
