package io.github.azizndao.trailer

import android.app.Application
import io.github.azizndao.network.NetworkModule
import io.github.azizndao.trailer.ui.UiModule
import io.github.azizndao.trailer.utils.UtilsModule
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
      modules(NetworkModule, UtilsModule, UiModule)
    }
  }
}