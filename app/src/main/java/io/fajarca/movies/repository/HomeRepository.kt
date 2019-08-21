package io.fajarca.movies.repository

import io.fajarca.movies.api.ApiService
import javax.inject.Inject

class HomeRepository @Inject constructor(private val apiService: ApiService) {

    fun nowPlaying() = apiService.nowPlaying()
}