package com.moviecommobile

import android.app.Application
import com.moviecommobile.core.di.initKoin
import org.koin.android.ext.koin.androidContext

class MovieComApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MovieComApplication)
        }
    }
}