package com.example.rxactivity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.ListPreference
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import com.hendraanggrian.app.RxActivity
import com.hendraanggrian.app.startActivityForResultAsCompletable
import com.hendraanggrian.app.startActivityForResultAsObservable
import com.hendraanggrian.app.startActivityForResultAsSingle
import io.reactivex.rxkotlin.subscribeBy
import kota.dialogs.snackbar
import kota.dialogs.toast
import kota.views.contentView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportFragmentManager.beginTransaction().replace(R.id.container, Content()).commitNow()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        RxActivity.onActivityResult(requestCode, resultCode, data)
    }

    class Content : PreferenceFragmentCompat() {
        private lateinit var typePreference: ListPreference
        private lateinit var activityPreference: Preference
        private lateinit var fragmentPreference: Preference

        private var type = "Observable"
        private val isObservable get() = type == "Observable"
        private val isSingle get() = type == "Single"
        private val isCompletable get() = type == "Completable"

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            addPreferencesFromResource(R.xml.activity_main)
            typePreference = findPreference("typePreference") as ListPreference
            activityPreference = findPreference("activityPreference") as Preference
            fragmentPreference = findPreference("fragmentPreference") as Preference

            typePreference.setOnPreferenceChangeListener { preference, newValue ->
                newValue as String
                preference.summary = "As $newValue"
                type = newValue
                false
            }
            activityPreference.setOnPreferenceClickListener {
                when {
                    isObservable -> activity.startActivityForResultAsObservable(Intent(context, NextActivity::class.java))
                            .subscribeBy(
                                    onNext = { _ -> onResultOK() },
                                    onError = { e -> onResultError(e) },
                                    onComplete = { onResultComplete() })
                    isSingle -> activity.startActivityForResultAsSingle(Intent(context, NextActivity::class.java))
                            .subscribeBy(
                                    onSuccess = { _ -> onResultOK() },
                                    onError = { e -> onResultError(e) })
                    isCompletable -> activity.startActivityForResultAsCompletable(Intent(context, NextActivity::class.java))
                            .subscribeBy(
                                    onComplete = { onResultComplete() },
                                    onError = { e -> onResultError(e) })
                }
                true
            }
            fragmentPreference.setOnPreferenceClickListener {
                when {
                    isObservable -> startActivityForResultAsObservable(Intent(context, NextActivity::class.java))
                            .subscribeBy(
                                    onNext = { _ -> onResultOK() },
                                    onError = { e -> onResultError(e) },
                                    onComplete = { onResultComplete() })
                    isSingle -> startActivityForResultAsSingle(Intent(context, NextActivity::class.java))
                            .subscribeBy(
                                    onSuccess = { _ -> onResultOK() },
                                    onError = { e -> onResultError(e) })
                    isCompletable -> startActivityForResultAsCompletable(Intent(context, NextActivity::class.java))
                            .subscribeBy(
                                    onComplete = { onResultComplete() },
                                    onError = { e -> onResultError(e) })
                }
                true
            }
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            RxActivity.onActivityResult(requestCode, resultCode, data)
        }

        private fun onResultOK() = activity.contentView!!.snackbar("onNext", android.R.string.ok) {}
        private fun onResultError(e: Throwable) = activity.contentView!!.snackbar("onError: ${e.message}", android.R.string.ok) {}
        private fun onResultComplete() = context.toast("onComplete")
    }
}