package io.fajarca.movies.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import io.fajarca.movies.vo.Result
import kotlinx.coroutines.Dispatchers

/**
 * The database serves as the single source of truth.
 * Therefore UI can receive data updates from database only.
 * Function notify UI about:
 * [Result.Status.SUCCESS] - with data from database
 * [Result.Status.ERROR] - if error has occurred from any source
 * [Result.Status.LOADING]
 */
fun <T, A> resultLiveData(
    loadFromDb: () -> LiveData<T>,
    createCall: suspend () -> Result<A>,
    saveCallResult: suspend (A) -> Unit
): LiveData<Result<T>> =

    liveData(Dispatchers.IO) {

        emit(Result.loading<T>())

        val dbSource = loadFromDb.invoke()
        val mappedDbSource = dbSource.map {
            Result.success(it)
        }

        emitSource(mappedDbSource)

        val responseStatus = createCall.invoke()

        if (responseStatus.status == Result.Status.SUCCESS) {
            saveCallResult(responseStatus.data!!)
        } else if (responseStatus.status == Result.Status.ERROR) {
            emit(
                Result.error(
                    responseStatus.message ?: "Unknown error", null
                )
            )
            emitSource(mappedDbSource)
        }
    }
