package io.fajarca.movies.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import io.fajarca.movies.base.BaseViewModel
import io.fajarca.movies.vo.Resource
import io.fajarca.movies.data.local.entity.Movie
import io.fajarca.movies.data.MoviesRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repository: MoviesRepository) : BaseViewModel() {

    private val language =  MutableLiveData<String>()

    val nowPlaying : LiveData<Resource<List<Movie>>> = Transformations.switchMap(language) {
        repository.getNowPlaying(it)
    }

    fun initData(languageId : String) {
        this.language.value = languageId
    }


}


