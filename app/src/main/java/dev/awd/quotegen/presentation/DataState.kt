package dev.awd.quotegen.presentation

sealed class DataState<out T> {
    data class Success<T>(val data: T) : DataState<T>()
    data class Error<T>(val data: T?, val exception: Exception) : DataState<T>()
    data class Loading<T>(val data: T?) : DataState<T>()

    fun toData(): T? = when (this) {
        is Success -> this.data
        is Loading -> this.data
        is Error -> this.data
    }

    fun isLoading(): Boolean? = if (this is Loading) true else null
    fun toErrorMessage(): String? = if (this is Error) this.exception.message else null
}