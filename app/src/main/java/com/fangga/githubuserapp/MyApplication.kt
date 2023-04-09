package com.fangga.githubuserapp

import android.app.Application
import com.fangga.core.di.databaseModule
import com.fangga.core.di.datastoreModule
import com.fangga.core.di.networkModule
import com.fangga.core.di.repositoryModule
import com.fangga.githubuserapp.di.useCaseModule
import com.fangga.githubuserapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    datastoreModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}