package io.fajarca.movies.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import io.fajarca.movies.base.BaseViewModel
import io.fajarca.movies.vo.Resource
import io.fajarca.movies.data.local.entity.Movie
import io.fajarca.movies.repository.HomeRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repository: HomeRepository) : BaseViewModel() {

    private val language =  MutableLiveData<String>()

    /*private val _nowPlaying = MutableLiveData<Result<List<Movie>>>()
    val nowPlaying: LiveData<Result<List<Movie>>>
        get() = _nowPlaying

    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    fun sum(x: Int, y: Int) = x + y

    fun onMainViewPressed() {
        viewModelScope.launch {
            delay(1_000)
            _message.value = "Hello, from coroutines!"
        }
    }
    fun nowPlaying() {

        val errorHandler = CoroutineExceptionHandler { _, exception ->
            // log to Crashlytics, logcat, etc.
        }


        viewModelScope.launch(Dispatchers.IO) {

            Timber.v("Now playing init ${Thread.currentThread().name}")
            setResultNowPlaying(Result.Loading())


            val response = getNowPlayingFromApi()
            insertAll(response.body()?.results ?: emptyList())

            val nowPlaying = getNowPlayingFromDb()
            // val response = getNowPlayingFromApi()

            if (nowPlaying.isEmpty()) setResultNowPlaying(Result.NoData()) else setResultNowPlaying(
                Result.HasData(nowPlaying)
            )
            *//*try {

                if (response.isSuccessful) {

                    val body = response.body()
                    val movies = body?.results ?: emptyList()

                    insertAll(movies)

                } else {

                }

            } catch (e: UnknownHostException) {
                Timber.e("Now playing api error : ${e.message}")
            }

            val nowPlaying = getNowPlayingFromDb()

            if (nowPlaying.isEmpty()) {
                setResultNowPlaying(Result.NoData())
            } else {
                setResultNowPlaying(Result.HasData(nowPlaying))
            }*//*

        }


    }

    private suspend fun getNowPlayingFromDb(): List<Movie> {
        Timber.v("Now playing DB ${Thread.currentThread().name}")
        return repository.findNowPlayingFromDb()
    }

    private suspend fun insertAll(movies: List<Movie>) {
        Timber.v("Inserting ${movies.size} to DB ${Thread.currentThread().name}")
        repository.insertAll(movies)
    }

    private suspend fun getNowPlayingFromApi(): Response<NowPlayingResponse> {

        return repository.findNowPlayingFromApi()
        *//*
         try {

             return withContext(Dispatchers.IO) {
                 repository.findNowPlayingFromApi()
             }

         } catch (e : Throwable) {

         } catch (e : IOException) {

         }*//*


    }


    private fun setResultNowPlaying(result: Result<List<Movie>>) {
        Timber.v("Now playing result ${Thread.currentThread().name}")
        _nowPlaying.postValue(result)
    }*/

    val nowPlaying : LiveData<Resource<List<Movie>>> = Transformations.switchMap(language) {
        repository.getNowPlaying(it)
    }

    fun initData(languageId : String) {
        this.language.value = languageId
    }


}


