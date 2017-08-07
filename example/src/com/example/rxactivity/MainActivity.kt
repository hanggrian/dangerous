package com.example.rxactivity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.errorbar
import android.support.v7.app.AppCompatActivity
import com.hendraanggrian.rx.activity.notifyRxActivity
import com.hendraanggrian.rx.activity.startActivityForResultAsObservable
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
        fab.setOnClickListener {
            startActivityForResultAsObservable(Intent(this, NextActivity::class.java))
                    .subscribeBy(
                            onNext = { _ -> errorbar(fab, "onNext", android.R.string.ok, {}) },
                            onError = { e -> errorbar(fab, "onError: ${e.message}", android.R.string.ok, {}) })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        notifyRxActivity(requestCode, resultCode, data)
    }
}