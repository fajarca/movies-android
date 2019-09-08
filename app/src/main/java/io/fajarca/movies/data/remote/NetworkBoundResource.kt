package io.fajarca.movies.data.remote

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import io.fajarca.movies.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// ResultType: Type for the Resource data. It's a DB data
// RequestType: Type for the API response.
abstract class NetworkBoundResource<ResultType, RequestType> {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {

        result.value = Resource.loading(null)
        val dbSource = loadFromDb()

        //Add db source to mediator live data

        result.addSource(dbSource) { data ->

            result.removeSource(dbSource)

            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    setValue(Resource.success(newData))
                }
            }

        }


    }

    @MainThread
    private fun setValue(newValue : Resource<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork(dbSource : LiveData<ResultType>) {
        val apiResponse = createCall()

        // We re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource) { newData ->
            setValue(Resource.loading(newData))
        }

        result.addSource(apiResponse) { response ->

            result.removeSource(apiResponse)
            result.removeSource(dbSource)

            when(response) {
                is ApiSuccessResponse -> {

                    GlobalScope.launch(Dispatchers.IO) {

                        saveCallResult(processResponse(response))

                        GlobalScope.launch(Dispatchers.Main) {
                            result.addSource(loadFromDb()) { newData ->
                                setValue(Resource.success(newData))
                            }
                        }

                    }

                }
                is ApiEmptyResponse -> {
                    GlobalScope.launch(Dispatchers.Main) {
                        result.addSource(loadFromDb()) { newData ->
                            setValue(Resource.success(newData))
                        }
                    }
                }
                is ApiErrorResponse -> {
                    onFetchFailed()
                    result.addSource(dbSource) { newData ->
                        setValue(Resource.error(response.errorMessage, newData))
                    }
                }
            }
        }
    }

    protected fun processResponse(response : ApiSuccessResponse<RequestType>) = response.body

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