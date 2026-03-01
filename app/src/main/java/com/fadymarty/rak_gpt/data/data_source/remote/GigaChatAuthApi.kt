package com.fadymarty.rak_gpt.data.data_source.remote

import com.fadymarty.rak_gpt.BuildConfig
import com.fadymarty.rak_gpt.data.data_source.remote.dto.requests.TokenRequestDto
import com.fadymarty.rak_gpt.data.data_source.remote.dto.TokenResponseDto
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.UUID

interface GigaChatAuthApi {

    @Headers("Authorization: Basic ${BuildConfig.AUTHORIZATION_KEY}")
    @POST("oath")
    suspend fun getAccessToken(
        @Header("RqUID") requestUid: String = UUID.randomUUID().toString(),
        @Body request: TokenRequestDto = TokenRequestDto(
            scope = "GIGACHAT_API_PERS"
        )
    ): TokenResponseDto

    companion object {
        const val BASE_URL = "https://ngw.devices.sberbank.ru:9443/api/v2/"
    }
}