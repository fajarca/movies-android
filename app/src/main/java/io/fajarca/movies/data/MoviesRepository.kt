package io.fajarca.movies.data

import androidx.lifecycle.LiveData
import io.fajarca.movies.data.local.dao.MovieDao
import io.fajarca.movies.data.local.dao.NowPlayingDao
import io.fajarca.movies.data.local.entity.Movie
import io.fajarca.movies.data.remote.MovieRemoteDataSource
import io.fajarca.movies.vo.Result
import io.fajarca.movies.vo.resultLiveData
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val dao: NowPlayingDao,
    private val movieDao : MovieDao
) {

    val nowPlaying = resultLiveData(
        loadFromDb = { dao.findAllNowPlaying() },
        createCall = { remoteDataSource.fetchNowPlaying() },
        saveCallResult =  { dao.insertAll(it.results)}
    )


    fun fetchMovieDetail(movieId : Long): LiveData<Result<List<Movie>>> {
        return resultLiveData(
            loadFromDb = { movieDao.findMovieById(movieId) },
            createCall = { remoteDataSource.fetchMovieDetail(movieId) },
            saveCallResult =  { movieDao.insert(it) } )
    }

}