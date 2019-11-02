package io.fajarca.movies.data.remote

import io.fajarca.movies.data.remote.response.CastResponse
import io.fajarca.movies.data.remote.response.MovieDetailsResponse
import io.fajarca.movies.data.remote.response.NowPlayingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("now_playing")
    suspend fun nowPlaying(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): Response<NowPlayingResponse>

    @GET("{movieId}")
    suspend fun movie(
        @Path("movieId") movieId: Long,
        @Query("language") language: String = "en-US"
    ): Response<MovieDetailsResponse>

    @GET("{movieId}/credits")
    suspend fun cast(
        @Path("movieId") movieId: Long
    ): Response<CastResponse>
}