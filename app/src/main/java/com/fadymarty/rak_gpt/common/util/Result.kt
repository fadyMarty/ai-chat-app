package com.fadymarty.rak_gpt.common.util

sealed interface Result<out T> {
    data class Success<out T>(val data: T) : Result<T>
    data class Failure(val exception: Throwable) : Result<Nothing>
}

inline fun <T, R> Result<T>.map(map: (T) -> R): Result<R> {
    return when (this) {
        is Result.Failure -> Result.Failure(exception)
        is Result.Success -> Result.Success(map(data))
    }
}

inline fun <T> Result<T>.onSuccess(action: (T) -> Unit): Result<T> {
    return when (this) {
        is Result.Failure -> this
        is Result.Success -> {
            action(data)
            this
        }
    }
}

inline fun <T> Result<T>.onFailure(action: (Throwable) -> Unit): Result<T> {
    return when (this) {
        is Result.Failure -> {
            action(exception)
            this
        }

        is Result.Success -> this
    }
}