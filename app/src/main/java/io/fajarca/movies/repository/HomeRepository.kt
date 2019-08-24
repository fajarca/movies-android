package io.fajarca.movies.repository

import io.fajarca.movies.api.ApiService
import io.fajarca.movies.db.dao.MovieDao
import io.fajarca.movies.db.entity.Movie
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val apiService: ApiService,
    private val dao: MovieDao
) {

    fun getNowPlaying(): Observable<List<Movie>> {
        return Observable.concatArrayEager(
            getNowPlayingFromDb(),
            getNowPlayingFromApi()
        )
    }

     private fun getNowPlayingFromDb(): Observable<List<Movie>> {
        return dao.findAllNowPlaying()
            .doOnNext {
                Timber.v("Dispatching ${it.size} movie from DB")
            }
            .doOnError {
                Timber.v("Error fetching from DB ${it.message}")
            }
    }

     private fun getNowPlayingFromApi(): Observable<List<Movie>> {
        return apiService.nowPlaying()
            .toObservable()
            .map {
                it.results
            }
            .doOnNext {
                Timber.v("Dispatching ${it.size} movie from API")
                insertMoviesToDb(it)
            }
            .doOnError {
                Timber.v("Error fetching from API ${it.message}")
            }
    }

    private fun insertMoviesToDb(movies: List<Movie>) {
        dao.insertAll(movies)
            .doOnComplete {
                Timber.v("Movies inserted to DB")
            }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe()
    }


}