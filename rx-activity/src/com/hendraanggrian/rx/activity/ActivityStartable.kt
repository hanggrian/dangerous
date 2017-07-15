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

    val manager: PackageManager

    @TargetApi(16)
    @RequiresApi(16)
    fun startActivityForResult(intent: Intent, requestCode: Int, options: Bundle?)

    fun startActivityForResult(intent: Intent, requestCode: Int)

    companion object {

        fun fromActivity(activity: Activity) = object : ActivityStartable {
            override val manager = activity.packageManager

            @TargetApi(16)
            @RequiresApi(16)
            override fun startActivityForResult(intent: Intent, requestCode: Int, options: Bundle?) = activity.startActivityForResult(intent, requestCode, options)

            override fun startActivityForResult(intent: Intent, requestCode: Int) = activity.startActivityForResult(intent, requestCode)
        }

        fun fromFragment(fragment: Fragment) = object : ActivityStartable {
            override val manager = if (Build.VERSION.SDK_INT >= 23) {
                fragment.context.packageManager
            } else if (Build.VERSION.SDK_INT >= 11) {
                fragment.activity.packageManager
            } else {
                throw IllegalStateException("Using RxActivity in Fragment requires API level 11 or higher. Use support Fragment or increase sdk min to 11.")
            }

            @TargetApi(16)
            @RequiresApi(16)
            override fun startActivityForResult(intent: Intent, requestCode: Int, options: Bundle?) = fragment.startActivityForResult(intent, requestCode, options)

            override fun startActivityForResult(intent: Intent, requestCode: Int) = if (Build.VERSION.SDK_INT >= 11) {
                fragment.startActivityForResult(intent, requestCode)
            } else {
                throw IllegalStateException("Using RxActivity in Fragment requires API level 11 or higher. Use support Fragment or increase sdk min to 11.")
            }
        }

        fun fromSupportFragment(fragment: android.support.v4.app.Fragment) = object : ActivityStartable {
            override val manager = fragment.context.packageManager

            @TargetApi(16)
            @RequiresApi(16)
            override fun startActivityForResult(intent: Intent, requestCode: Int, options: Bundle?) = fragment.startActivityForResult(intent, requestCode, options)

            override fun startActivityForResult(intent: Intent, requestCode: Int) = fragment.startActivityForResult(intent, requestCode)
        }
    }
}