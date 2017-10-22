package com.example.rxactivity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.ListPreference
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import android.view.View
import com.hendraanggrian.app.RxActivity
import com.hendraanggrian.app.startActivityForResultAsCompletable
import com.hendraanggrian.app.startActivityForResultAsObservable
import com.hendraanggrian.app.startActivityForResultAsSingle
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
        setSupportActionBar(toolbar)
        if (supportFragmentManager.findNullable<Fragment>(TAG_RETAINED_FRAGMENT) == null) {
            supportFragmentManager.addNow(R.id.container, Content(), TAG_RETAINED_FRAGMENT)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        RxActivity.onActivityResult(requestCode, resultCode, data)
    }

    override fun onClick(v: View?) {
        when (sharedPreferences.getString("typePreference")) {
            "Observable" -> startActivityForResultAsObservable(Intent(this, NextActivity::class.java))
                    .subscribeBy(
                            onNext = { _ -> onResultOK() },
                            onError = { e -> onResultError(e) },
                            onComplete = { onResultComplete() })
            "Single" -> startActivityForResultAsSingle(Intent(this, NextActivity::class.java))
                    .subscribeBy(
                            onSuccess = { _ -> onResultOK() },
                            onError = { e -> onResultError(e) })
            "Completable" -> startActivityForResultAsCompletable(Intent(this, NextActivity::class.java))
                    .subscribeBy(
                            onComplete = { onResultComplete() },
                            onError = { e -> onResultError(e) })
        }
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
                when (typePreference.value) {
                    "Observable" -> startActivityForResultAsObservable(Intent(context, NextActivity::class.java))
                            .subscribeBy(
                                    onNext = { _ -> onResultOK() },
                                    onError = { e -> onResultError(e) },
                                    onComplete = { onResultComplete() })
                    "Single" -> startActivityForResultAsSingle(Intent(context, NextActivity::class.java))
                            .subscribeBy(
                                    onSuccess = { _ -> onResultOK() },
                                    onError = { e -> onResultError(e) })
                    "Completable" -> startActivityForResultAsCompletable(Intent(context, NextActivity::class.java))
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