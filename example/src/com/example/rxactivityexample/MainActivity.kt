package com.example.rxactivityexample

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
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
                            onNext = { _ ->
                                showSnackbar("onNext")
                            },
                            onError = { e ->
                                showSnackbar("onError:\n" + e.message)
                            }
                    )
        }
        button2.setOnClickListener {
            startActivityForResultBy(intent)
                    .subscribeBy(
                            onNext = { result ->
                                showSnackbar("onNext:\n" + result.toString())
                            },
                            onError = { e ->
                                showSnackbar("onError:\n" + e.message)
                            }
                    )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        RxActivity.onActivityResult(requestCode, resultCode, data)
    }

    private fun showSnackbar(text: String) = Snackbar.make(scrollView, text, Snackbar.LENGTH_INDEFINITE)
            .setAction(android.R.string.ok, {})
            .let {
                (it.view.findViewById(android.support.design.R.id.snackbar_text) as TextView).maxLines = 5
                it.show()
            }
}