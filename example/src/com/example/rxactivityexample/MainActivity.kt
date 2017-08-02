package com.example.rxactivityexample

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.errorbar
import android.support.v7.app.AppCompatActivity
import com.hendraanggrian.rx.activity.RxActivity
import com.hendraanggrian.rx.activity.RxActivity.startActivityForResultBy
import com.hendraanggrian.rx.activity.RxActivity.startActivityForResultOk
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
            startActivityForResultOk(intent)
                    .subscribeBy(
                            onNext = { _ -> errorbar(coordinatorLayout, "onNext", android.R.string.ok, {}) },
                            onError = { e -> errorbar(coordinatorLayout, "onError: ${e.message}", android.R.string.ok, {}) })
        }
        button2.setOnClickListener {
            startActivityForResultBy(intent)
                    .subscribeBy(
                            onNext = { result -> errorbar(coordinatorLayout, "onNext:\n$result", android.R.string.ok, {}) },
                            onError = { e -> errorbar(coordinatorLayout, "onError: ${e.message}", android.R.string.ok, {}) })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        RxActivity.onActivityResult(requestCode, resultCode, data)
    }
}