package io.fajarca.movies.data

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.*
import io.fajarca.movies.base.BaseDataSource
import io.fajarca.movies.vo.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class NetworkBoundResources<EntityData, ResponseData> {

    @MainThread
    abstract fun loadFromDb(): LiveData<EntityData>

    @MainThread
    abstract fun shouldFetch(data: EntityData?): Boolean

    @MainThread
    abstract suspend fun createCall(): Result<ResponseData>

    @WorkerThread
    abstract suspend fun saveCallResult(response: ResponseData)

    fun asLiveData(): LiveData<Result<EntityData>> = liveData(Dispatchers.IO) {
        emit(Result.loading(null))

        val dbSource = loadFromDb()
        val mappedDbSource = dbSource.map { Result.success(it) }

        emitSource(mappedDbSource)

        if (shouldFetch(mappedDbSource.value?.data)) {

            val response = createCall()

            if (response.status == Result.Status.SUCCESS) {
                response.data?.let { saveCallResult(it) }
            } else {
                emit(Result.error(response.message ?: "Unknown error", null))
                emitSource(mappedDbSource)
            }

        }

    }

}