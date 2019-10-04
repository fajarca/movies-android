package io.fajarca.movies.vo

data class Result<out T>(val status: Status, val data : T?, val message : String?) {


    enum class Status {
        SUCCESS,
        ERROR,
        LOADING,
        EMPTY
    }

    companion object {
        fun <T> success(data: T): Result<T> {
            return Result(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): Result<T> {
            return Result(Status.ERROR, data, message)
        }

        fun <T> loading(data: T? = null): Result<T> {
            return Result(Status.LOADING, data, null)
        }

        fun <T> empty() : Result<T> {
            return Result(Status.EMPTY, null, null)
        }
    }

}