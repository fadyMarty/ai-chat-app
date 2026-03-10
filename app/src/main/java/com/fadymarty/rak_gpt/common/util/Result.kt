package com.fadymarty.rak_gpt.common.util

sealed interface Result<out T> {
    data class Success<out T>(val data: T) : Result<T>
    data class Failure(val error: Throwable) : Result<Nothing>
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
            action(error)
            this
        }

        is Result.Success -> this
    }
}