package com.example.rxactivityexample

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Errorbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.hendraanggrian.rx.activity.RxActivity
import com.hendraanggrian.rx.activity.startActivityForResultBy
import com.hendraanggrian.rx.activity.startActivityForResultOk
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
                            onNext = { _ -> showErrorbar("onNext") },
                            onError = { e -> showErrorbar("onError:\n" + e.message) })
        }
        button2.setOnClickListener {
            startActivityForResultBy(intent)
                    .subscribeBy(
                            onNext = { result -> showErrorbar("onNext:\n" + result.toString()) },
                            onError = { e -> showErrorbar("onError:\n" + e.message) })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        RxActivity.onActivityResult(requestCode, resultCode, data)
    }

    private fun showErrorbar(text: String) = Errorbar.make(coordinatorLayout, text, Errorbar.LENGTH_INDEFINITE)
            .setBackdropResource(R.drawable.errorbar_bg_cloud)
            .setAction(android.R.string.ok, View.OnClickListener {})
            .show()
}