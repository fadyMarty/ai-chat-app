package com.fadymarty.rak_gpt

import android.app.Application
import com.fadymarty.rak_gpt.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RakGptApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@RakGptApp)
            modules(appModule)
        }
    }
}