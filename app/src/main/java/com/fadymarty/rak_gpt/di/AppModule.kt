package com.fadymarty.rak_gpt.di

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.fadymarty.rak_gpt.common.util.Constants
import com.fadymarty.rak_gpt.data.data_source.remote.ChatRemoteDataSource
import com.fadymarty.rak_gpt.data.data_source.remote.ChatRemoteDataSourceImpl
import com.fadymarty.rak_gpt.data.data_source.remote.GigaChatApi
import com.fadymarty.rak_gpt.data.data_source.remote.GigaChatAuthenticator
import com.fadymarty.rak_gpt.data.data_source.remote.GigaChatInterceptor
import com.fadymarty.rak_gpt.data.manager.TokenManagerImpl
import com.fadymarty.rak_gpt.data.repository.AndroidAudioPlayer
import com.fadymarty.rak_gpt.data.repository.AndroidAudioRecorder
import com.fadymarty.rak_gpt.data.repository.ChatRepositoryImpl
import com.fadymarty.rak_gpt.domain.manager.TokenManager
import com.fadymarty.rak_gpt.domain.repository.AudioPlayer
import com.fadymarty.rak_gpt.domain.repository.AudioRecorder
import com.fadymarty.rak_gpt.domain.repository.ChatRepository
import com.fadymarty.rak_gpt.presentation.chat.ChatViewModel
import com.linc.amplituda.Amplituda
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

val appModule = module {

    single {
        PreferenceDataStoreFactory.create(
            produceFile = {
                androidContext().preferencesDataStoreFile(
                    name = Constants.DATASTORE_NAME
                )
            }
        )
    }

    single {
        Json { ignoreUnknownKeys = true }
    }

    singleOf(::GigaChatInterceptor)
    singleOf(::GigaChatAuthenticator)
    single {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(get<GigaChatInterceptor>())
            .authenticator(get<GigaChatAuthenticator>())
            .build()
    }

    single {
        val json = Json {
            ignoreUnknownKeys = true
        }
        val contentType = "application/json".toMediaType()

        Retrofit.Builder()
            .baseUrl(GigaChatApi.BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(get())
            .build()
            .create(GigaChatApi::class.java)
    }
    singleOf(::ChatRemoteDataSourceImpl) { bind<ChatRemoteDataSource>() }

    singleOf(::Amplituda)

    singleOf(::TokenManagerImpl) { bind<TokenManager>() }
    singleOf(::ChatRepositoryImpl) { bind<ChatRepository>() }
    singleOf(::AndroidAudioRecorder) { bind<AudioRecorder>() }
    singleOf(::AndroidAudioPlayer) { bind<AudioPlayer>() }

    viewModelOf(::ChatViewModel)
}