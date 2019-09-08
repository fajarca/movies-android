package io.fajarca.movies.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import io.fajarca.movies.vo.Resource
import io.fajarca.movies.data.local.entity.Movie
import io.fajarca.movies.data.MoviesRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repository: MoviesRepository) : ViewModel() {

    private val language =  MutableLiveData<String>()

    val nowPlaying : LiveData<Resource<List<Movie>>> = Transformations.switchMap(language) {
        repository.getNowPlaying(it)
    }

    fun initData(languageId : String) {
        this.language.value = languageId
    }


}


