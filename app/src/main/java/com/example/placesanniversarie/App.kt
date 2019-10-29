package com.example.placesanniversarie

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication


class App : MultiDexApplication() {


    private var context: Context? = null

    fun getContext(): Context? {

        return context
    }

    override fun onCreate() {

        super.onCreate()

        context = applicationContext


    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}