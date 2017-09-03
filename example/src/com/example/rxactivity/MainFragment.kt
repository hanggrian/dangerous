package com.example.rxactivity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.errorbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hendraanggrian.rx.activity.startActivityForResultAsObservable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment: Fragment() {

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater?.inflate(R.layout.fragment_main, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    fragmentButton.setOnClickListener {
      startActivityForResultAsObservable(Intent(activity, NextActivity::class.java))
          .subscribeBy(
              onNext = { _ -> errorbar(fragmentButton, "onNext", android.R.string.ok, {}) },
              onError = { e -> errorbar(fragmentButton, "onError: ${e.message}", android.R.string.ok, {}) })
    }
  }
}
