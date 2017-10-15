package com.example.rxactivity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.errorbar
import android.support.v7.app.AppCompatActivity
import com.hendraanggrian.app.RxActivity
import com.hendraanggrian.app.startActivityForResultAsObservable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        button.setOnClickListener {
            startActivityForResultAsObservable(Intent(this, NextActivity::class.java))
                    .subscribeBy(
                            onNext = { _ -> errorbar(button, "onNext", android.R.string.ok, {}) },
                            onError = { e -> errorbar(button, "onError: ${e.message}", android.R.string.ok, {}) })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        RxActivity.onActivityResult(requestCode, resultCode, data)
    }
}