package io.fajarca.movies.base

import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableBoolean
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Job

abstract class BaseViewModel : ViewModel() {

    private var isLoading = ObservableBoolean()
    var mCompositeDisposable = CompositeDisposable()

    fun getCompositeDisposable() = mCompositeDisposable


    fun setLoading(setAsLoading: Boolean) = isLoading.set(setAsLoading)
    fun isLoading() = isLoading

    override fun onCleared() {
        mCompositeDisposable.dispose()
        super.onCleared()
    }


}