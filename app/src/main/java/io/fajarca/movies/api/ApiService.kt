package io.fajarca.movies.api

import io.fajarca.movies.model.NowPlayingResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET


interface ApiService {

    @GET("now_playing")
    fun nowPlaying() : Single<NowPlayingResponse>

}