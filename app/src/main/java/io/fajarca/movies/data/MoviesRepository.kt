package io.fajarca.movies.data

import io.fajarca.movies.data.local.dao.NowPlayingDao
import io.fajarca.movies.data.remote.MovieRemoteDataSource
import io.fajarca.movies.vo.resultLiveData
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val dao: NowPlayingDao
) {

    val nowPlaying = resultLiveData(
        loadFromDb = { dao.findAllNowPlaying() },
        createCall = { remoteDataSource.fetchNowPlaying() },
        saveCallResult =  { dao.insertAll(it.results)}
    )

    val movieDetail = resultLiveData(
        loadFromDb = { dao.findAllNowPlaying() },
        createCall = { remoteDataSource.fetchNowPlaying() },
        saveCallResult =  { dao.insertAll(it.results)}
    )

}