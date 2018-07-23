package com.hendraanggrian.appcompat.dispatcher.demo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceFragmentCompat
import com.hendraanggrian.appcompat.dispatcher.Dispatcher
import com.hendraanggrian.appcompat.dispatcher.R
import com.hendraanggrian.appcompat.dispatcher.startActivity
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
        supportFragmentManager.beginTransaction()
            .add(Fragment(), "asdasd")
            .commitAllowingStateLoss()
        supportFragmentManager.executePendingTransactions()
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