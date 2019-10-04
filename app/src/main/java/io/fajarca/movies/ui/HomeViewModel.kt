package io.fajarca.movies.ui

import androidx.lifecycle.ViewModel
import io.fajarca.movies.data.MoviesRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repository: MoviesRepository) : ViewModel() {

    val nowPlaying = repository.nowPlaying
    val movieDetail = repository.movieDetail

}


