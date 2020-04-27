package com.example.launchy

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_next.*

class NextActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next)
    }

    fun respond(view: View) {
        when (view) {
            okButton -> setResult(RESULT_OK)
            cancelButton -> setResult(RESULT_CANCELED)
            firstButton -> setResult(RESULT_FIRST_USER)
        }
        finish()
    }
}