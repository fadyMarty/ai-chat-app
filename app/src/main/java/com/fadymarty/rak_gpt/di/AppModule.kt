package com.fadymarty.rak_gpt.di

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.fadymarty.rak_gpt.common.util.Constants
import com.fadymarty.rak_gpt.data.data_source.remote.GigaChatAuthenticator
import com.fadymarty.rak_gpt.data.data_source.remote.GigaChatInterceptor
import com.fadymarty.rak_gpt.data.data_source.remote.OkHttpRemoteChatDataSource
import com.fadymarty.rak_gpt.data.data_source.remote.RemoteChatDataSource
import com.fadymarty.rak_gpt.data.manager.TokenManagerImpl
import com.fadymarty.rak_gpt.data.repository.ChatRepositoryImpl
import com.fadymarty.rak_gpt.domain.manager.TokenManager
import com.fadymarty.rak_gpt.domain.repository.ChatRepository
import com.fadymarty.rak_gpt.presentation.chat.ChatViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

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

    singleOf(::OkHttpRemoteChatDataSource) { bind<RemoteChatDataSource>() }

    singleOf(::TokenManagerImpl) { bind<TokenManager>() }
    singleOf(::ChatRepositoryImpl) { bind<ChatRepository>() }

    viewModelOf(::ChatViewModel)
}