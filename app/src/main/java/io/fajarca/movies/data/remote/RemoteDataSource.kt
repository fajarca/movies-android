package io.fajarca.movies.data.remote

import io.fajarca.movies.base.BaseRemoteDataSource
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val api: ApiService) : BaseRemoteDataSource() {

    suspend fun nowPaying() = getApiResult {  api.nowPlaying() }
    suspend fun fetchMovie(movieId : Long) = getApiResult {  api.movie(movieId) }
    suspend fun fetchCast(movieId: Long) = getApiResult { api.cast(movieId) }
}