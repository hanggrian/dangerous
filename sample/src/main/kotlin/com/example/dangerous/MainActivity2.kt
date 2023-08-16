package com.example.dangerous

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.AppBarLayout
import com.hendraanggrian.appcompat.dangerous.requirePermission
import com.priyankvasa.android.cameraviewex.CameraView

class MainActivity2 : AppCompatActivity() {
    lateinit var appbarLayout: AppBarLayout
    lateinit var toolbar: Toolbar
    lateinit var cameraView: CameraView

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appbarLayout = findViewById(R.id.appbarLayout)
        toolbar = findViewById(R.id.toolbar)
        cameraView = findViewById(R.id.cameraView)
        setSupportActionBar(toolbar)

        requirePermission(Manifest.permission.CAMERA)
    }

    @SuppressLint("MissingPermission")
    override fun onResume() {
        super.onResume()
        cameraView.start()
    }

    override fun onPause() {
        cameraView.stop()
        super.onPause()
    }

    override fun onDestroy() {
        cameraView.destroy()
        super.onDestroy()
    }
}
