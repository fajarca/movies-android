package io.fajarca.movies.data.remote.response

import io.fajarca.movies.base.BaseDataSource
import io.fajarca.movies.data.remote.ApiService
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(private val apiService: ApiService) : BaseDataSource() {
    suspend fun fetchData() = getResult { apiService.nowPlaying() }
}