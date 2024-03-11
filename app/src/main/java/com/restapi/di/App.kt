package com.restapi.di

import android.app.Application
import com.restapi.di.interfaces.AppComponent
import com.restapi.di.interfaces.DaggerAppComponent
import com.restapi.di.module.RetrofitModule
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App : Application() {

}