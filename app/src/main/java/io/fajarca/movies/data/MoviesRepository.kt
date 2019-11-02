package io.fajarca.movies.data

import androidx.lifecycle.LiveData
import androidx.room.withTransaction
import io.fajarca.movies.base.BaseRepository
import io.fajarca.movies.data.local.MoviesDatabase
import io.fajarca.movies.data.local.dao.GenreDao
import io.fajarca.movies.data.local.dao.MovieDao
import io.fajarca.movies.data.local.dao.MovieGenreJunctionDao
import io.fajarca.movies.data.local.dao.NowPlayingDao
import io.fajarca.movies.data.local.dao.CastDao
import io.fajarca.movies.data.local.entity.Cast
import io.fajarca.movies.data.local.entity.NowPlaying
import io.fajarca.movies.data.local.join.MovieWithGenres
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
    private val genreDao: GenreDao,
    private val movieGenreDao: MovieGenreJunctionDao,
    private val castDao: CastDao,
    private val mapper: MovieDetailMapper,
    private val apiService: ApiService,
    private val db: MoviesDatabase
) : BaseRepository() {

    fun fetchNowPlaying(): LiveData<Result<List<NowPlaying>>> {
        return object : NetworkBoundResources<List<NowPlaying>, NowPlayingResponse>() {
            override fun loadFromDb(): LiveData<List<NowPlaying>> = nowPlayingDao.findAllNowPlaying()
            override fun shouldFetch(data: List<NowPlaying>?): Boolean = true
            override suspend fun createCall(): Result<NowPlayingResponse> = getApiResult { apiService.nowPlaying() }
            override suspend fun saveCallResult(response: NowPlayingResponse) = nowPlayingDao.deleteAndInsertInTransaction(response.results)
        }.asLiveData()
    }

    fun fetchMovieDetail(movieId: Long): LiveData<Result<MovieWithGenres>> {
        return object : NetworkBoundResources<MovieWithGenres, MovieDetailsResponse>() {
            override fun loadFromDb(): LiveData<MovieWithGenres> = movieGenreDao.findMovieWithCategories(movieId)
            override fun shouldFetch(data: MovieWithGenres?): Boolean = data == null
            override suspend fun createCall(): Result<MovieDetailsResponse> = getApiResult { apiService.movie(movieId) }
            override suspend fun saveCallResult(response: MovieDetailsResponse) {
                db.withTransaction {
                    movieDao.insert(mapper.mapMovieResponseToMovie(response))
                    genreDao.insertAll(mapper.mapMovieResponseToCategory(response))
                    movieGenreDao.insertAll(mapper.mapGenreToMovieCategory(movieId, response.genres))
                }
            }
        }.asLiveData()
    }

    fun fetchCasts(movieId: Long): LiveData<Result<List<Cast>>> {
        return object : NetworkBoundResources<List<Cast>, CastResponse>() {
            override fun loadFromDb(): LiveData<List<Cast>> = castDao.findAll(movieId)
            override fun shouldFetch(data: List<Cast>?) = data == null
            override suspend fun createCall(): Result<CastResponse> = getApiResult { apiService.cast(movieId) }
            override suspend fun saveCallResult(response: CastResponse) {
                val casts = mapper.mapCastResponseToCast(movieId, response)
                castDao.deleteAndInsertInTransaction(movieId, casts)
            }
        }.asLiveData()
    }

    fun sum(x : Int, y : Int) = x+ y
}