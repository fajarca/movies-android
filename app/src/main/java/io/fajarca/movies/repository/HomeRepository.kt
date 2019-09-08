package io.fajarca.movies.repository

import androidx.lifecycle.LiveData
import io.fajarca.movies.data.remote.ApiResponse
import io.fajarca.movies.data.remote.ApiService
import io.fajarca.movies.data.remote.NetworkBoundResource
import io.fajarca.movies.vo.Resource
import io.fajarca.movies.data.local.dao.MovieDao
import io.fajarca.movies.data.local.entity.Movie
import io.fajarca.movies.model.NowPlayingResponse
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val apiService: ApiService,
    private val dao: MovieDao
) {

    fun getNowPlaying(language : String) : LiveData<Resource<List<Movie>>> {

        return object : NetworkBoundResource<List<Movie>, NowPlayingResponse>() {
            override fun saveCallResult(item: NowPlayingResponse) = dao.insertAll(item.results)
            override fun shouldFetch(data: List<Movie>): Boolean = true
            override fun loadFromDb(): LiveData<List<Movie>> = dao.findAllNowPlaying()
            override fun createCall(): LiveData<ApiResponse<NowPlayingResponse>> = apiService.nowPlaying(language = language)

        }.asLiveData()

    }


}