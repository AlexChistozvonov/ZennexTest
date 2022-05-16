package com.example.zennextest

import android.app.Application
import android.content.Context
import com.example.zennextest.core.di.AppComponent
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ZennexApplication : Application() {
    private var _appComponent: AppComponent? = null

    val appComponent: AppComponent
        get() = checkNotNull(_appComponent)


val Context.appComponent: AppComponent
    get() = when (this) {
        is ZennexApplication -> appComponent
        else -> (applicationContext as ZennexApplication).appComponent
    }
}