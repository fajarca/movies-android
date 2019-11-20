package io.fajarca.movies.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers
import io.fajarca.movies.vo.Result

fun <SavedData, Response> resultLiveData(
        loadFromDb : () -> LiveData<SavedData>,
        createCall : suspend  () -> Result<Response>,
        saveCallResult : suspend (Response) -> Unit
) : LiveData<Result<SavedData>> = liveData(Dispatchers.IO) {

    emit(Result.loading(null))

    val db = loadFromDb.invoke().map { Result.success(it) }
    emitSource(db)

    val response = createCall.invoke()

    if (response.status == Result.Status.SUCCESS) {
        saveCallResult(response.data!!)
    } else {
        emit(Result.error(response.message ?: "Unknown error", null))
        emitSource(db)
    }

}

fun <Response> resultLiveData(createCall : suspend  () -> Result<Response>) = liveData(Dispatchers.IO) {
    emit(Result.loading(null))
    val response = createCall.invoke()
    emit(response)
}