package io.fajarca.movies.data.remote

import androidx.lifecycle.LiveData
import io.fajarca.movies.model.NowPlayingResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("now_playing")
    fun nowPlaying(
        @Query("language")  language : String = "en-US",
        @Query("page")  page : Int = 1
    ) : LiveData<ApiResponse<NowPlayingResponse>>

}