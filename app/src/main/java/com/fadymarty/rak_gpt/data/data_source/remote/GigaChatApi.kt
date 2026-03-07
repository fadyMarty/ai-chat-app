package com.fadymarty.rak_gpt.data.data_source.remote

import com.fadymarty.rak_gpt.data.data_source.remote.dto.FileResponseDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Streaming

interface GigaChatApi {

    @Multipart
    @POST("files")
    suspend fun uploadFile(
        @Part file: MultipartBody.Part,
        @Part("purpose") purpose: RequestBody = "general".toRequestBody(),
    ): FileResponseDto

    @Streaming
    @GET("files/{file_id}/content")
    suspend fun downloadFile(
        @Path("file_id") id: String,
    ): ResponseBody

    companion object {
        const val BASE_URL = "https://gigachat.devices.sberbank.ru/api/v1/"
    }
}