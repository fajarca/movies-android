package io.fajarca.movies.ui.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import io.fajarca.movies.data.MoviesRepository
import io.fajarca.movies.data.local.entity.Movie
import io.fajarca.movies.vo.Result
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(private val repository: MoviesRepository) : ViewModel() {


    private val movieId = MutableLiveData<Long>()

    fun setData(movieId : Long) {
        this.movieId.value = movieId
    }

    val movieDetail : LiveData<Result<List<Movie>>> = Transformations.switchMap(movieId, ::fetchMovieDetail)

    private fun fetchMovieDetail(movieId: Long) = repository.fetchMovieDetail(movieId)

}

