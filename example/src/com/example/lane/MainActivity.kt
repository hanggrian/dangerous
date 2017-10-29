package com.example.lane

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import com.hendraanggrian.lane.onActivityResult2
import com.hendraanggrian.lane.startActivityForResult
import kota.addNow
import kota.debug
import kota.dialogs.OkButton
import kota.dialogs.supportAlert
import kota.find
import kota.findNullable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG_RETAINED_FRAGMENT = "RetainedFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        debug("$this onCreate")
        setSupportActionBar(toolbar)
        if (supportFragmentManager.findNullable<Fragment>(TAG_RETAINED_FRAGMENT) == null) {
            supportFragmentManager.addNow(R.id.container, Content(), TAG_RETAINED_FRAGMENT)
        }
        fragmentManager.beginTransaction()
                .add(android.app.Fragment(), "asdasd")
                .commitAllowingStateLoss()
        fragmentManager.executePendingTransactions()
        toolbar2.setOnClickListener {
            startActivityForResult(Intent(this, NextActivity::class.java).putExtra("from", "activity")) { _, _ ->
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
        onActivityResult2(requestCode, resultCode, data)
        debug("$this onActivityResult")
    }

    class Content : PreferenceFragmentCompat() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            retainInstance = true
        }

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            addPreferencesFromResource(R.xml.activity_main)
            find<Preference>("fragmentPreference").setOnPreferenceClickListener {
                startActivityForResult(Intent(context, NextActivity::class.java).putExtra("from", "fragment")) { resultCode, _ ->
                    supportAlert("Result code", resultCode.toString(), OkButton)
                }
                false
            }
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            onActivityResult2(requestCode, resultCode, data)
        }
    }
}