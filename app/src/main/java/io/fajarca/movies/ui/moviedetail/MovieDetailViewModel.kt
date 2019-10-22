package io.fajarca.movies.ui.moviedetail

import androidx.lifecycle.*
import io.fajarca.movies.data.MoviesRepository
import io.fajarca.movies.data.local.entity.Cast
import io.fajarca.movies.data.local.join.MovieWithGenres
import io.fajarca.movies.vo.Result
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(private val repository: MoviesRepository) :
    ViewModel() {

    private val movieId = MutableLiveData<Long>()
    private val movId = MutableLiveData<Long>()

    val movieDetail: LiveData<Result<MovieWithGenres>> = Transformations.switchMap(movieId) {
        repository.fetchMovieDetail(it)
    }
    val casts : LiveData<Result<List<Cast>>> = Transformations.switchMap(movId) {
        repository.fetchCasts(it)
    }

    fun initData(movieId: Long) {
        this.movieId.value = movieId
    }

    fun setMovieId(movId: Long) {
        this.movId.value = movId
    }
}