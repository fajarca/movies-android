package io.fajarca.movies.ui.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import io.fajarca.movies.data.MoviesRepository
import io.fajarca.movies.data.local.entity.Cast
import io.fajarca.movies.data.local.join.MovieCategory
import io.fajarca.movies.vo.Result
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(private val repository: MoviesRepository) : ViewModel() {

    private val movieId = MutableLiveData<Long>()
    val movieDetail: LiveData<Result<List<MovieCategory>>> = Transformations.switchMap(movieId, ::fetchMovieDetail)
    val casts : LiveData<Result<List<Cast>>> = Transformations.switchMap(movieId, ::fetchCasts)

    fun setData(movieId: Long) {
        this.movieId.value = movieId
    }

    private fun fetchMovieDetail(movieId: Long) = repository.fetchMovieDetail(movieId)
    private fun fetchCasts(movieId: Long) = repository.fetchCasts(movieId)
}