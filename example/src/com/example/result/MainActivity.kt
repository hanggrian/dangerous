package com.example.result

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.PreferenceFragmentCompat
import com.example.dispatcher.R
import com.hendraanggrian.dispatcher.Dispatcher
import com.hendraanggrian.dispatcher.startActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity(), AnkoLogger {

    companion object {
        const val TAG_RETAINED_FRAGMENT = "RetainedFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        debug("$this onCreate")
        setSupportActionBar(toolbar)
        if (supportFragmentManager.findFragmentByTag(TAG_RETAINED_FRAGMENT) == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, Content(), TAG_RETAINED_FRAGMENT)
                .commitNow()
        }
        fragmentManager.beginTransaction()
            .add(android.app.Fragment(), "asdasd")
            .commitAllowingStateLoss()
        fragmentManager.executePendingTransactions()
        toolbar2.setOnClickListener {
            startActivity(Intent(this, NextActivity::class.java).putExtra("from", "activity")) { _, _ ->
                debug("$this callback")
                textView.text = "ok result"
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        refWatcher.watch(this)
        debug("$this onDestroy")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Dispatcher.onActivityResult(this, requestCode, resultCode, data)
        debug("$this onActivityResult")
    }

    class Content : PreferenceFragmentCompat() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            retainInstance = true
        }

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            addPreferencesFromResource(R.xml.activity_main)
            findPreference("fragmentPreference").setOnPreferenceClickListener {
                startActivity(Intent(context, NextActivity::class.java).putExtra("from", "fragment")) { resultCode, _ ->
                    context!!.toast("Result code $resultCode")
                }
                false
            }
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            Dispatcher.onActivityResult(this, requestCode, resultCode, data)
        }
    }
}