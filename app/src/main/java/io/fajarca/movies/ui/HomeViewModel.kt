package io.fajarca.movies.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.id.ajsmsig.cs.simpel.base.BaseViewModel
import io.fajarca.movies.model.NowPlayingResponse
import io.fajarca.movies.model.NowPlayingResult
import io.fajarca.movies.repository.HomeRepository
import io.fajarca.movies.util.extensions.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import io.fajarca.movies.common.Result
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repository: HomeRepository) : BaseViewModel() {

    private val _nowPlaying = MutableLiveData<Result<List<NowPlayingResult>>>()
    val nowPlaying: LiveData<Result<List<NowPlayingResult>>>
        get() = _nowPlaying

    fun getNowPlaying() {

        mCompositeDisposable += repository.nowPlaying()
            .doOnSubscribe{ setResultNowPlaying(Result.Loading())}
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<NowPlayingResponse>() {
                override fun onSuccess(t: NowPlayingResponse) {
                    if (t.results.isEmpty()) {
                        setResultNowPlaying(Result.NoData())
                        return
                    }

                    setResultNowPlaying(Result.HasData(t.results))
                }

                override fun onError(e: Throwable) {

                }
            })
    }

    private fun setResultNowPlaying(data : Result<List<NowPlayingResult>>) {
        _nowPlaying.postValue(data)
    }


}