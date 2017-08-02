package com.hendraanggrian.rx.activity.internal

import android.annotation.TargetApi
import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
internal interface ActivityStartable {

    val context: Context

    @TargetApi(16)
    @RequiresApi(16)
    fun startActivityForResult(intent: Intent, requestCode: Int, options: Bundle?)

    fun startActivityForResult(intent: Intent, requestCode: Int)

    companion object {

        fun fromActivity(activity: Activity) = object : ActivityStartable {
            override val context = activity

            @TargetApi(16)
            @RequiresApi(16)
            override fun startActivityForResult(intent: Intent, requestCode: Int, options: Bundle?) = activity.startActivityForResult(intent, requestCode, options)

            override fun startActivityForResult(intent: Intent, requestCode: Int) = activity.startActivityForResult(intent, requestCode)
        }

        fun fromFragment(fragment: Fragment) = object : ActivityStartable {
            override val context = if (Build.VERSION.SDK_INT >= 23) {
                fragment.context
            } else {
                fragment.activity
            }

            @TargetApi(16)
            @RequiresApi(16)
            override fun startActivityForResult(intent: Intent, requestCode: Int, options: Bundle?) = fragment.startActivityForResult(intent, requestCode, options)

            override fun startActivityForResult(intent: Intent, requestCode: Int) = fragment.startActivityForResult(intent, requestCode)
        }

        fun fromSupportFragment(fragment: android.support.v4.app.Fragment) = object : ActivityStartable {
            override val context = fragment.context

            @TargetApi(16)
            @RequiresApi(16)
            override fun startActivityForResult(intent: Intent, requestCode: Int, options: Bundle?) = fragment.startActivityForResult(intent, requestCode, options)

            override fun startActivityForResult(intent: Intent, requestCode: Int) = fragment.startActivityForResult(intent, requestCode)
        }
    }
}