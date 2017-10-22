package com.example.rxactivity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import kota.finishWithResult
import kotlinx.android.synthetic.main.activity_next.*

class NextActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val RESULT_CUSTOM = 9152
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next)
        setSupportActionBar(toolbar)
        okButton.setOnClickListener(this)
        canceledButton.setOnClickListener(this)
        customButton.setOnClickListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View) = finishWithResult(when (v) {
        okButton -> RESULT_OK
        canceledButton -> RESULT_CANCELED
        else -> RESULT_CUSTOM
    }, Intent())
}