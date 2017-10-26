package com.example.rxactivity

import android.app.Application
import android.content.Context
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher

class App : Application() {

    internal lateinit var mRefWatcher: RefWatcher

    override fun onCreate() {
        super.onCreate()
        if (!LeakCanary.isInAnalyzerProcess(this)) {
            mRefWatcher = LeakCanary.install(this)
        }
    }
}

val Context.refWatcher: RefWatcher get() = (applicationContext as App).mRefWatcher