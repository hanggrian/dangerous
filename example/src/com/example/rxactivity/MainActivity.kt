package com.example.rxactivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import android.view.View
import com.hendraanggrian.app.onActivityResult2
import com.hendraanggrian.app.startActivityForResult2
import kota.addNow
import kota.debug
import kota.dialogs.OkButton
import kota.dialogs.supportAlert
import kota.find
import kota.findNullable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

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
        toolbar2.setOnClickListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        debug("$this onDestroy")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        onActivityResult2(requestCode, resultCode, data)
    }

    override fun onClick(v: View) {
        startActivityForResult2(Intent(this, NextActivity::class.java).putExtra("from", "ACTIVITY")) { requestCode, resultCode, _ ->
            debug("$this Callback")
            supportAlert("Callback", "requestCode = $requestCode\nresultCode = $resultCode", OkButton)
            if (resultCode == Activity.RESULT_OK) {
                supportActionBar!!.title = "damn you all to hell"
            }
        }
    }

    class Content : PreferenceFragmentCompat() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            retainInstance = true
        }

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            addPreferencesFromResource(R.xml.activity_main)
            find<Preference>("fragmentPreference").setOnPreferenceClickListener {
                startActivityForResult2(Intent(context, NextActivity::class.java).putExtra("from", "FRAGMENT")) { requestCode, resultCode, _ ->
                    debug("$this Callback")
                    supportAlert("Callback", "requestCode = $requestCode\nresultCode = $resultCode", OkButton)
                }
                false
            }
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            onActivityResult2(requestCode, resultCode, data)
        }
    }
}