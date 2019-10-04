package io.fajarca.movies.data.remote

import io.fajarca.movies.base.BaseDataSource
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(private val apiService: ApiService) :
    BaseDataSource() {

    suspend fun fetchNowPlaying() = getResult { apiService.nowPlaying() }
    suspend fun fetchMovieDetail(movieId: Long) = getResult { apiService.movie(movieId) }

}