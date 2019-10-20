package io.fajarca.movies.data

import androidx.lifecycle.LiveData
import io.fajarca.movies.base.BaseRepository
import io.fajarca.movies.data.local.MoviesDatabase
import io.fajarca.movies.data.local.dao.*
import io.fajarca.movies.data.local.entity.Cast
import io.fajarca.movies.data.local.entity.MovieCategoryJoin
import io.fajarca.movies.data.local.entity.NowPlaying
import io.fajarca.movies.data.local.join.MovieCategory
import io.fajarca.movies.data.remote.ApiService
import io.fajarca.movies.data.remote.mapper.moviedetail.MovieDetailMapper
import io.fajarca.movies.data.remote.response.CastResponse
import io.fajarca.movies.data.remote.response.MovieDetailsResponse
import io.fajarca.movies.data.remote.response.NowPlayingResponse
import io.fajarca.movies.vo.Result
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val nowPlayingDao: NowPlayingDao,
    private val movieDao: MovieDao,
    private val categoryDao: CategoryDao,
    private val movieCategoryDao: MovieCategoryDao,
    private val castDao : CastDao,
    private val mapper: MovieDetailMapper,
    private val apiService: ApiService
    ) : BaseRepository() {

    fun fetchNowPlaying(): LiveData<Result<List<NowPlaying>>> {
        return object : NetworkBoundResources<List<NowPlaying>, NowPlayingResponse>() {
            override fun loadFromDb(): LiveData<List<NowPlaying>> = nowPlayingDao.findAllNowPlaying()
            override fun shouldFetch(data: List<NowPlaying>?): Boolean = true
            override suspend fun createCall(): Result<NowPlayingResponse> =  getApiResult { apiService.nowPlaying() }
            override suspend fun saveCallResult(response: NowPlayingResponse) = nowPlayingDao.deleteAndInsertInTransaction(response.results)
        }.asLiveData()
    }

    fun fetchMovieDetail(movieId: Long): LiveData<Result<List<MovieCategory>>> {
        return object : NetworkBoundResources<List<MovieCategory>, MovieDetailsResponse>() {
            override fun loadFromDb(): LiveData<List<MovieCategory>> = movieCategoryDao.findMovieWithCategory(movieId)
            override fun shouldFetch(data: List<MovieCategory>?): Boolean = true
            override suspend fun createCall(): Result<MovieDetailsResponse> = getApiResult { apiService.movie(movieId) }
            override suspend fun saveCallResult(response: MovieDetailsResponse) {
                movieDao.insert(mapper.mapMovieResponseToMovie(response))
                categoryDao.insertAll(mapper.mapMovieResponseToCategory(response))
                response.genres.forEach {
                    movieCategoryDao.insert(MovieCategoryJoin(response.id, it.id))
                }
            }
        }.asLiveData()
    }

    fun fetchCasts(movieId: Long) : LiveData<Result<List<Cast>>> {
        return object : NetworkBoundResources<List<Cast>, CastResponse>() {
            override fun loadFromDb(): LiveData<List<Cast>> = castDao.findAll(movieId)
            override fun shouldFetch(data: List<Cast>?) = true
            override suspend fun createCall(): Result<CastResponse> = getApiResult { apiService.cast(movieId) }
            override suspend fun saveCallResult(response: CastResponse) {
                val casts = mapper.mapCastResponseToCast(movieId, response)
                castDao.deleteAndInsertInTransaction(movieId, casts)
            }

        }.asLiveData()
    }
}