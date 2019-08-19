package co.id.ajsmsig.cs.simpel.common

import androidx.annotation.StringRes

sealed class Result<out T> {
    class NoInternetConnection<out T> : Result<T>()
    class Loading<out T> : Result<T>()
    class NoData<out T> : Result<T>()
    data class HasData<out T>(val data : T) : Result<T>()
    data class Error<out T> (@StringRes val errorMessage : Int): Result<T>()
    data class Information<out T> (val data : T): Result<T>()
}