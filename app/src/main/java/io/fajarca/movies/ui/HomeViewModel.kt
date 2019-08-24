package io.fajarca.movies.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.id.ajsmsig.cs.simpel.base.BaseViewModel
import io.fajarca.movies.R
import io.fajarca.movies.model.NowPlayingResponse
import io.fajarca.movies.repository.HomeRepository
import io.fajarca.movies.util.extensions.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import io.fajarca.movies.common.Result
import io.fajarca.movies.db.entity.Movie
import io.reactivex.observers.DisposableObserver
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repository: HomeRepository) : BaseViewModel() {

    private val _nowPlaying = MutableLiveData<Result<List<Movie>>>()
    val nowPlaying: LiveData<Result<List<Movie>>>
        get() = _nowPlaying

    fun getNowPlaying() {
        mCompositeDisposable += repository.getNowPlaying()
            .doOnSubscribe { setResultNowPlaying(Result.Loading()) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<List<Movie>>(){
                override fun onComplete() {

                }

                override fun onNext(t: List<Movie>) {

                    if (t.isEmpty()) setResultNowPlaying(Result.NoData()) else setResultNowPlaying(Result.HasData(t))

                }

                override fun onError(e: Throwable) {
                    setResultNowPlaying(Result.Error(R.string.unknown_error))
                }
            })
    }

    private fun setResultNowPlaying(data : Result<List<Movie>>) {
        _nowPlaying.postValue(data)
    }


}