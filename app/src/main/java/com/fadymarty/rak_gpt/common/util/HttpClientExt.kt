package com.fadymarty.rak_gpt.common.util

import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeCall(
    execute: suspend () -> T,
): Result<T> {
    try {
        val response = execute()
        return Result.Success(response)
    } catch (e: HttpException) {
        e.printStackTrace()
        return Result.Failure(e)
    } catch (e: IOException) {
        e.printStackTrace()
        return Result.Failure(e)
    }
}