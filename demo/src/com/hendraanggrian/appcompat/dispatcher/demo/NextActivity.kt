package com.hendraanggrian.appcompat.dispatcher.demo

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.hendraanggrian.appcompat.dispatcher.R
import com.hendraanggrian.bundler.Bundler
import com.hendraanggrian.bundler.Extra
import kotlinx.android.synthetic.main.activity_next.*

class NextActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val RESULT_CUSTOM = 9152
    }

    @Extra lateinit var from: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next)
        setSupportActionBar(toolbar)

        Bundler.bindExtras(this)
        supportActionBar!!.title = "from $from"

        okButton.setOnClickListener(this)
        canceledButton.setOnClickListener(this)
        customButton.setOnClickListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View) {
        setResult(when (v) {
            okButton -> RESULT_OK
            canceledButton -> RESULT_CANCELED
            else -> RESULT_CUSTOM
        })
        finish()
    }
}