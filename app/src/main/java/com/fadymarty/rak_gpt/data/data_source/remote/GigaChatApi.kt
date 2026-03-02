package com.fadymarty.rak_gpt.data.data_source.remote

import com.fadymarty.rak_gpt.BuildConfig
import com.fadymarty.rak_gpt.data.data_source.remote.dto.TokenResponseDto
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.UUID

interface GigaChatApi {

    @FormUrlEncoded
    @Headers("Authorization: Basic ${BuildConfig.AUTHORIZATION_KEY}")
    @POST("oauth")
    suspend fun getAccessToken(
        @Header("RqUID") requestUid: String = UUID.randomUUID().toString(),
        @Field("scope") scope: String = "GIGACHAT_API_PERS",
    ): TokenResponseDto

    companion object {
        const val BASE_URL = "https://ngw.devices.sberbank.ru:9443/api/v2/"
    }
}