package io.fajarca.movies.repository

import io.fajarca.movies.api.ApiService
import io.fajarca.movies.db.dao.MovieDao
import io.fajarca.movies.db.entity.Movie
import javax.inject.Inject

class HomeRepository @Inject constructor(private val apiService: ApiService, private val dao : MovieDao) {

    fun nowPlaying() = apiService.nowPlaying()
    fun insertMovies(movies : List<Movie>) = dao.insertAll(movies)
}