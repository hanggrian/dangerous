package com.hendraanggrian.appcompat.dangerous

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hendraanggrian.appcompat.dangerous.test.R

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_AppCompat_Light_NoActionBar)
    }
}
