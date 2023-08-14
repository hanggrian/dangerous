package com.example.hallpass

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hendraanggrian.appcompat.hallpass.Hallpass
import com.hendraanggrian.appcompat.hallpass.PermissionCallback
import com.hendraanggrian.appcompat.hallpass.requestPermissions

class MainActivity : AppCompatActivity(), PermissionCallback by Hallpass {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermissions(Manifest.permission.CAMERA) {
            Toast.makeText(this, if (it) "Granted" else "Denied", Toast.LENGTH_SHORT).show()
        }
    }
}
