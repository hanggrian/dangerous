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
import kota.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportFragmentManager.replaceNow(R.id.container, Content())
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
            typePreference = find("typePreference")
            activityPreference = find("activityPreference")
            fragmentPreference = find("fragmentPreference")

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
                                    onNext = { _ -> (activity as MainActivity).onResultOK() },
                                    onError = { e -> (activity as MainActivity).onResultError(e) },
                                    onComplete = { (activity as MainActivity).onResultComplete() })
                    isSingle -> activity.startActivityForResultAsSingle(Intent(context, NextActivity::class.java))
                            .subscribeBy(
                                    onSuccess = { _ -> (activity as MainActivity).onResultOK() },
                                    onError = { e -> (activity as MainActivity).onResultError(e) })
                    isCompletable -> activity.startActivityForResultAsCompletable(Intent(context, NextActivity::class.java))
                            .subscribeBy(
                                    onComplete = { (activity as MainActivity).onResultComplete() },
                                    onError = { e -> (activity as MainActivity).onResultError(e) })
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

    private fun onResultOK() = contentView!!.snackbar("onNext", android.R.string.ok) {}
    private fun onResultError(e: Throwable) = contentView!!.snackbar("onError: ${e.message}", android.R.string.ok) {}
    private fun onResultComplete() = toast("onComplete")
}