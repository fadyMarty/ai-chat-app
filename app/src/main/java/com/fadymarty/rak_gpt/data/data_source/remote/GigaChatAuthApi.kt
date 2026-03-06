package com.fadymarty.rak_gpt.data.data_source.remote

import com.fadymarty.rak_gpt.BuildConfig
import com.fadymarty.rak_gpt.data.data_source.remote.dto.TokenResponseDto
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST
import java.util.UUID

interface GigaChatAuthApi {

    @FormUrlEncoded
    @POST("oauth")
    suspend fun getAccessToken(
        @Header("Authorization") token: String = "Basic ${BuildConfig.AUTHORIZATION_KEY}",
        @Header("RqUID") requestId: String = UUID.randomUUID().toString(),
        @Field("scope") scope: String = "GIGACHAT_API_PERS",
    ): TokenResponseDto

    companion object {
        const val BASE_URL = "https://ngw.devices.sberbank.ru:9443/api/v2/"
    }
}