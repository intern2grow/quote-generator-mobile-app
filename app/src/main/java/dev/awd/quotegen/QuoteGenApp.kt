package dev.awd.quotegen

import android.app.Application
import dev.awd.quotegen.di.AppModule
import dev.awd.quotegen.di.AppModuleImpl

class QuoteGenApp : Application() {
    companion object {
        lateinit var appModule: AppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(applicationContext)
    }
}