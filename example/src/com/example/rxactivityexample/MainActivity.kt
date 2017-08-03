package com.example.rxactivityexample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.errorbar
import android.support.v7.app.AppCompatActivity
import com.hendraanggrian.rx.activity.*
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val intent = Intent(this, NextActivity::class.java)
        button1.setOnClickListener {
            startActivityForOk(intent)
                    .subscribeBy(
                            onNext = { _ -> errorbar(coordinatorLayout, "onNext", android.R.string.ok, {}) },
                            onError = { e -> errorbar(coordinatorLayout, "onError: ${e.message}", android.R.string.ok, {}) })
        }
        button2.setOnClickListener {
            startActivityForAny(intent)
                    .subscribeBy(
                            onNext = { result ->
                                val print = "ActivityResult[requestCode=${result.getRequestCode()}, resultCode=${when (result.getResultCode()) {
                                    Activity.RESULT_OK -> "RESULT_OK"
                                    Activity.RESULT_CANCELED -> "RESULT_CANCELED"
                                    else -> result.getResultCode().toString()
                                }}]"
                                errorbar(coordinatorLayout, "onNext:\n$print", android.R.string.ok, {})
                            },
                            onError = { e -> errorbar(coordinatorLayout, "onError: ${e.message}", android.R.string.ok, {}) })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        onActivityResultBy(requestCode, resultCode, data)
    }
}