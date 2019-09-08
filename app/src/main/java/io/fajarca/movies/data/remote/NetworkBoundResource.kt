package io.fajarca.movies.data.remote

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import io.fajarca.movies.vo.Resource

// ResultType: Type for the Resource data. It's a DB data
// RequestType: Type for the API response.
abstract class NetworkBoundResource<ResultType, RequestType> {

    private val result = MediatorLiveData<Resource<ResultType>>()
    /**
     * Called to save result of the API response into the database
     */
    @WorkerThread
    protected abstract fun saveCallResult(item : RequestType)

    /**
     * Called with the data in the database to decide whether to fetch potentially updated data from the network
     */
    @MainThread
    protected abstract fun shouldFetch(data : ResultType) : Boolean

    /**
     * Called to get the cached data from the database
     */
    @MainThread
    protected abstract fun loadFromDb() : LiveData<ResultType>

    /**
     * Called to create the API call
     */
    @MainThread
    protected abstract fun createCall() : LiveData<ApiResponse<RequestType>>

    /**
     * Called when the fetchs fails. The child class may want to reset component like rate limiter
     */
    protected open fun onFetchFailed() {}


    fun asLiveData() : LiveData<Resource<ResultType>> = result




}