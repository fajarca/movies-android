package io.fajarca.movies.data

import androidx.lifecycle.LiveData
import io.fajarca.movies.base.BaseRepository
import io.fajarca.movies.data.local.dao.*
import io.fajarca.movies.data.local.entity.Cast
import io.fajarca.movies.data.local.entity.MovieCategoryJoin
import io.fajarca.movies.data.local.entity.NowPlaying
import io.fajarca.movies.data.local.join.MovieCategory
import io.fajarca.movies.data.remote.ApiService
import io.fajarca.movies.data.remote.mapper.moviedetail.MovieResponseToCategoryMapper
import io.fajarca.movies.data.remote.mapper.moviedetail.MovieResponseToMovieMapper
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
    private val mapper: MovieResponseToMovieMapper,
    private val movieToCategoryMapper: MovieResponseToCategoryMapper,
    private val apiService: ApiService
) : BaseRepository() {

    fun fetchNowPlaying(): LiveData<Result<List<NowPlaying>>> {
        return object : NetworkBoundResources<List<NowPlaying>, NowPlayingResponse>() {

            override fun loadFromDb(): LiveData<List<NowPlaying>> {
                return nowPlayingDao.findAllNowPlaying()
            }

            override fun shouldFetch(data: List<NowPlaying>?): Boolean = true

            override suspend fun createCall(): Result<NowPlayingResponse> {
                return getApiResult { apiService.nowPlaying() }
            }

            override suspend fun saveCallResult(response: NowPlayingResponse) {
                nowPlayingDao.insertAll(response.results)
            }
        }.asLiveData()
    }

    fun fetchMovieDetail(movieId: Long): LiveData<Result<List<MovieCategory>>> {

        return object : NetworkBoundResources<List<MovieCategory>, MovieDetailsResponse>() {

            override fun loadFromDb(): LiveData<List<MovieCategory>> {
                return movieCategoryDao.findMovieWithCategory(movieId)
            }

            override fun shouldFetch(data: List<MovieCategory>?): Boolean = true

            override suspend fun createCall(): Result<MovieDetailsResponse> {
                return getApiResult { apiService.movie(movieId) }
            }

            override suspend fun saveCallResult(response: MovieDetailsResponse) {
                movieDao.insert(mapper.map(response))
                categoryDao.insertAll(movieToCategoryMapper.map(response))
                response.genres.forEach {
                    movieCategoryDao.insert(MovieCategoryJoin(response.id, it.id))
                }
            }
        }.asLiveData()
    }

    fun fetchCasts(movieId: Long) : LiveData<Result<List<Cast>>> {
        return object : NetworkBoundResources<List<Cast>, CastResponse>() {
            override fun loadFromDb(): LiveData<List<Cast>> {
                return castDao.findAll(movieId)
            }

            override fun shouldFetch(data: List<Cast>?) = true

            override suspend fun createCall(): Result<CastResponse> {
                return getApiResult { apiService.cast(movieId) }
            }

            override suspend fun saveCallResult(response: CastResponse) {
                val casts = mutableListOf<Cast>()
                response.cast.forEach {
                    casts.add(Cast(movieId, it.id, it.character ?: "", it.creditId ?: "", it.name ?: "", it.profilePath ?: ""))
                }
                castDao.deleteAndInsertTransaction(movieId, casts)
            }

        }.asLiveData()
    }
}