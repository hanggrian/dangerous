package com.example.result

import android.app.Application
import android.content.Context
import com.hendraanggrian.bundler.Bundler
import com.hendraanggrian.preferencer.Preferencer
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher

class App : Application() {

    internal lateinit var mRefWatcher: RefWatcher

    override fun onCreate() {
        super.onCreate()
        Bundler.setDebug(BuildConfig.DEBUG)
        Preferencer.isDebug = BuildConfig.DEBUG

        if (!LeakCanary.isInAnalyzerProcess(this)) {
            mRefWatcher = LeakCanary.install(this)
        }
    }
}

val Context.refWatcher: RefWatcher get() = (applicationContext as App).mRefWatcher