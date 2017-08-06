package com.example.rxactivity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.hendraanggrian.kota.app.finishWithResult
import kotlinx.android.synthetic.main.activity_next.*

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class NextActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val RESULT_CUSTOM = 9152
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next)
        setSupportActionBar(toolbar)
        buttonOk.setOnClickListener(this)
        buttonCanceled.setOnClickListener(this)
        buttonCustom.setOnClickListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View) = finishWithResult(when (v) {
        buttonOk -> RESULT_OK
        buttonCanceled -> RESULT_CANCELED
        else -> RESULT_CUSTOM
    }, Intent())
}