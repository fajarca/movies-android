package io.fajarca.movies.data

import io.fajarca.movies.data.local.dao.MovieDao
import io.fajarca.movies.data.remote.response.MovieRemoteDataSource
import io.fajarca.movies.vo.resultLiveData
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val dao: MovieDao
) {

    val nowPlaying = resultLiveData(
        loadFromDb = { dao.findAllNowPlaying() },
        createCall = { remoteDataSource.fetchData() },
        saveCallResult =  { dao.insertAll(it.results)}
    )

}