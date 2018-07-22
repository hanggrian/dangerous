package com.hendraanggrian.dispatcher.demo

import android.app.Application
import android.content.Context
import com.hendraanggrian.bundler.Bundler
import com.hendraanggrian.preferencer.Preferencer
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher

class App : Application() {

    @PublishedApi
    internal lateinit var mRefWatcher: RefWatcher

    override fun onCreate() {
        super.onCreate()
        Bundler.setDebug(BuildConfig.DEBUG)
        Preferencer.setDebug(BuildConfig.DEBUG)

        if (!LeakCanary.isInAnalyzerProcess(this)) {
            mRefWatcher = LeakCanary.install(this)
        }
    }
}

inline val Context.refWatcher: RefWatcher get() = (applicationContext as App).mRefWatcher