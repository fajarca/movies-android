package io.fajarca.movies.data

import androidx.lifecycle.LiveData
import io.fajarca.movies.base.BaseDataSource
import io.fajarca.movies.data.local.dao.MovieDao
import io.fajarca.movies.data.local.dao.NowPlayingDao
import io.fajarca.movies.data.local.entity.Movie
import io.fajarca.movies.data.local.entity.NowPlaying
import io.fajarca.movies.data.remote.ApiService
import io.fajarca.movies.data.remote.mapper.MovieDetailMapper
import io.fajarca.movies.data.remote.response.MovieDetailsResponse
import io.fajarca.movies.data.remote.response.NowPlayingResponse
import io.fajarca.movies.vo.Result
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val dao: NowPlayingDao,
    private val movieDao : MovieDao,
    private val mapper: MovieDetailMapper,
    private val apiService: ApiService
)  : BaseDataSource() {


    fun fetchNowPlaying(): LiveData<Result<List<NowPlaying>>> {
        return object : NetworkBoundResources<List<NowPlaying>, NowPlayingResponse>() {

            override fun loadFromDb(): LiveData<List<NowPlaying>> {
                return dao.findAllNowPlaying()
            }


            override fun shouldFetch(data: List<NowPlaying>?): Boolean = true

            override suspend fun createCall(): Result<NowPlayingResponse> {
                return getResult { apiService.nowPlaying() }
            }

            override suspend fun saveCallResult(response: NowPlayingResponse) {
                dao.insertAll(response.results)
            }

        }.asLiveData()
    }

    fun fetchMovieDetail(movieId : Long): LiveData<Result<Movie>> {

        return object : NetworkBoundResources<Movie, MovieDetailsResponse>() {
           override fun loadFromDb(): LiveData<Movie> {
               return movieDao.findMovieById(movieId)
           }

           override fun shouldFetch(data: Movie?): Boolean = true

           override suspend fun createCall(): Result<MovieDetailsResponse> {
               return getResult { apiService.movie(movieId) }
           }

           override suspend fun saveCallResult(response: MovieDetailsResponse) {
               movieDao.insert(mapper.map(response))
           }


       }.asLiveData()
    }

}