package com.fadymarty.rak_gpt.data.data_source.remote

import com.fadymarty.rak_gpt.data.data_source.remote.dto.TokenResponseDto
import com.fadymarty.rak_gpt.domain.manager.TokenManager
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Authenticator
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.io.IOException

class GigaChatAuthenticator(
    private val tokenManager: TokenManager
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            try {
                val newAccessToken = getAccessToken()

                tokenManager.saveAccessToken(newAccessToken.accessToken)
                response.request.newBuilder()
                    .header("Authorization", "Bearer ${newAccessToken.accessToken}")
                    .build()
            } catch (e: IOException) {
                tokenManager.deleteAccessToken()
                null
            } catch (e: HttpException) {
                tokenManager.deleteAccessToken()
                null
            }
        }
    }

    private suspend fun getAccessToken(): TokenResponseDto {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val json = Json {
            ignoreUnknownKeys = true
        }
        val contentType = "application/json".toMediaType()

        val authApi = Retrofit.Builder()
            .baseUrl(GigaChatAuthApi.BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(okHttpClient)
            .build()
            .create(GigaChatAuthApi::class.java)

        return authApi.getAccessToken()
    }
}