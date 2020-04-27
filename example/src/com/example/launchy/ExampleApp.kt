package com.example.launchy

import android.app.Application
import com.hendraanggrian.appcompat.launchy.Launchy
import com.hendraanggrian.prefy.PreferencesLogger
import com.hendraanggrian.prefy.Prefy
import com.hendraanggrian.prefy.android.Android

class ExampleApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Launchy.setDebug()
        Prefy.setLogger(PreferencesLogger.Android)
    }
}