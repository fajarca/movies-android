package io.fajarca.movies.data

import androidx.room.withTransaction
import io.fajarca.movies.data.local.MoviesDatabase
import io.fajarca.movies.data.local.dao.*
import io.fajarca.movies.data.remote.RemoteDataSource
import io.fajarca.movies.data.remote.mapper.moviedetail.MovieDetailMapper
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val nowPlayingDao: NowPlayingDao,
    private val movieDao: MovieDao,
    private val genreDao: GenreDao,
    private val movieGenreDao: MovieGenreJunctionDao,
    private val castDao: CastDao,
    private val mapper: MovieDetailMapper,
    private val db: MoviesDatabase,
    private val remoteDataSource: RemoteDataSource
) {

    fun fetchNowPlaying() = resultLiveData(
        loadFromDb = { nowPlayingDao.findAllNowPlaying() },
        createCall = { remoteDataSource.nowPaying() },
        saveCallResult = { nowPlayingDao.deleteAndInsertInTransaction(it.results) }
    )

    fun fetchMovieDetail(movieId: Long) = resultLiveData(
        loadFromDb = { movieGenreDao.findMovieWithCategories(movieId) },
        createCall = {  remoteDataSource.fetchMovie(movieId)},
        saveCallResult = {
            db.withTransaction {
                movieDao.insert(mapper.mapMovieResponseToMovie(it))
                genreDao.insertAll(mapper.mapMovieResponseToCategory(it))
                movieGenreDao.insertAll(
                    mapper.mapGenreToMovieCategory(
                        movieId,
                        it.genres
                    )
                )
            }
        }
    )

    fun fetchCasts(movieId: Long) = resultLiveData(
        loadFromDb = { castDao.findAll(movieId) },
        createCall = {  remoteDataSource.fetchCast(movieId) },
        saveCallResult = {
            val casts = mapper.mapCastResponseToCast(movieId, it)
            castDao.deleteAndInsertInTransaction(movieId, casts)
        }
    )

}