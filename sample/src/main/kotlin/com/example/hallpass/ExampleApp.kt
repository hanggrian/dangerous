package com.example.hallpass

import android.app.Application
import com.hendraanggrian.appcompat.hallpass.Hallpass

class ExampleApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Hallpass.setDebug()
    }
}
