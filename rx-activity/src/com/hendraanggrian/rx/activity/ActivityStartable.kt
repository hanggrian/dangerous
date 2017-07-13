package com.hendraanggrian.rx.activity

import android.annotation.TargetApi
import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
internal interface ActivityStartable {

    val packageManager: PackageManager

    fun startActivityForResult(intent: Intent, requestCode: Int)

    @TargetApi(16)
    @RequiresApi(16)
    fun startActivityForResult(intent: Intent, requestCode: Int, options: Bundle?)

    companion object {
        fun fromActivity(activity: Activity) = object : ActivityStartable {
            override val packageManager = activity.packageManager

            override fun startActivityForResult(intent: Intent, requestCode: Int) {
                activity.startActivityForResult(intent, requestCode)
            }

            @TargetApi(16)
            @RequiresApi(16)
            override fun startActivityForResult(intent: Intent, requestCode: Int, options: Bundle?) {
                activity.startActivityForResult(intent, requestCode, options)
            }
        }

        fun fromFragment(fragment: Fragment) = object : ActivityStartable {
            override val packageManager = if (Build.VERSION.SDK_INT >= 23) {
                fragment.context.packageManager
            } else if (Build.VERSION.SDK_INT >= 11) {
                fragment.activity.packageManager
            } else {
                throw RuntimeException("Using RxActivity in Fragment requires API level 11 or higher. Use support Fragment or increase sdk min to 11.")
            }

            override fun startActivityForResult(intent: Intent, requestCode: Int) {
                if (Build.VERSION.SDK_INT >= 11) {
                    fragment.startActivityForResult(intent, requestCode)
                } else {
                    throw RuntimeException("Using RxActivity in Fragment requires API level 11 or higher. Use support Fragment or increase sdk min to 11.")
                }
            }

            @TargetApi(16)
            @RequiresApi(16)
            override fun startActivityForResult(intent: Intent, requestCode: Int, options: Bundle?) {
                fragment.startActivityForResult(intent, requestCode, options)
            }
        }

        fun fromSupportFragment(fragment: android.support.v4.app.Fragment) = object : ActivityStartable {
            override val packageManager = fragment.context.packageManager

            override fun startActivityForResult(intent: Intent, requestCode: Int) {
                fragment.startActivityForResult(intent, requestCode)
            }

            @TargetApi(16)
            @RequiresApi(16)
            override fun startActivityForResult(intent: Intent, requestCode: Int, options: Bundle?) {
                fragment.startActivityForResult(intent, requestCode, options)
            }
        }
    }
}