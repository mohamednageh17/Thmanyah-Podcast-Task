package com.example.thmanyah_podcast_task

import android.app.Application
import com.example.data.di.dataModule
import com.example.data.di.networkModule
import com.example.domain.di.domainModule
import com.example.thmanyah_podcast_task.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class ThmanyahApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize Timber for logging
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        // Initialize Koin
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@ThmanyahApplication)
            modules(
                networkModule,
                dataModule,
                domainModule,
                appModule
            )
        }
    }
}


