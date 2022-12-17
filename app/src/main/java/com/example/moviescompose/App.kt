package com.example.moviescompose

import android.app.Application
import com.example.moviescompose.BuildConfig
import com.example.moviescompose.data.RepoModule
import com.example.moviescompose.ui.UiModule
import com.example.moviescompose.utils.UtilsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import timber.log.Timber


class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(RepoModule, UtilsModule, UiModule)
        }
    }
}