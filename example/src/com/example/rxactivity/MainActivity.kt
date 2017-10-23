package com.example.rxactivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.ListPreference
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import android.view.View
import com.hendraanggrian.app.*
import io.reactivex.rxkotlin.subscribeBy
import kota.*
import kota.dialogs.OkButton
import kota.dialogs.supportAlert
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
        debug("$this onCreate")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        onActivityResult2(requestCode, resultCode, data)
        //RxActivity.onActivityResult(requestCode, resultCode, data)
    }

    override fun onClick(v: View) {
        startActivityForResult2(Intent(this, NextActivity::class.java).putExtra("type", "TEST"), Activity.RESULT_OK, { _, _, _ ->
            onResultOK()
            supportActionBar!!.title = "damn you all to hell"
            debug("$this onNext")
        }) { _, _, _ ->
        }
        /*val type = sharedPreferences.getString("typePreference", "Observable")
        when (type) {
            "Observable" -> startActivityForResultAsObservable(Intent(this, NextActivity::class.java).putExtra("type", type))
                    .subscribeBy(
                            onNext = { _ -> onResultOK() },
                            onError = { e -> onResultError(e) },
                            onComplete = { onResultComplete() })
            "Single" -> startActivityForResultAsSingle(Intent(this, NextActivity::class.java).putExtra("type", type))
                    .subscribeBy(
                            onSuccess = { _ -> onResultOK() },
                            onError = { e -> onResultError(e) })
            "Completable" -> startActivityForResultAsCompletable(Intent(this, NextActivity::class.java).putExtra("type", type))
                    .subscribeBy(
                            onComplete = { onResultComplete() },
                            onError = { e -> onResultError(e) })
        }*/
    }

    private fun onResultOK() = supportAlert("Result", "onNext", OkButton)
    private fun onResultError(e: Throwable) = supportAlert("Result", "onError: ${e.message}", OkButton)
    private fun onResultComplete() = toast("onComplete")

    class Content : PreferenceFragmentCompat() {
        private lateinit var typePreference: ListPreference
        private lateinit var fragmentPreference: Preference

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            retainInstance = true
        }

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            addPreferencesFromResource(R.xml.activity_main)
            typePreference = find("typePreference")
            fragmentPreference = find("fragmentPreference")

            typePreference.value?.let { typePreference.summary = "As $it" }
            typePreference.setOnPreferenceChangeListener { preference, newValue ->
                preference.summary = "As $newValue"
                true
            }

            fragmentPreference.setOnPreferenceClickListener {
                val type = typePreference.value ?: "Observable"
                when (type) {
                    "Observable" -> startActivityForResultAsObservable(Intent(context, NextActivity::class.java).putExtra("type", type))
                            .subscribeBy(
                                    onNext = { _ -> onResultOK() },
                                    onError = { e -> onResultError(e) },
                                    onComplete = { onResultComplete() })
                    "Single" -> startActivityForResultAsSingle(Intent(context, NextActivity::class.java).putExtra("type", type))
                            .subscribeBy(
                                    onSuccess = { _ -> onResultOK() },
                                    onError = { e -> onResultError(e) })
                    "Completable" -> startActivityForResultAsCompletable(Intent(context, NextActivity::class.java).putExtra("type", type))
                            .subscribeBy(
                                    onComplete = { onResultComplete() },
                                    onError = { e -> onResultError(e) })
                }
                false
            }
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            RxActivity.onActivityResult(requestCode, resultCode, data)
        }

        private fun onResultOK() = supportAlert("Result", "onNext", OkButton)
        private fun onResultError(e: Throwable) = supportAlert("Result", "onError: ${e.message}", OkButton)
        private fun onResultComplete() = context.toast("onComplete")
    }
}