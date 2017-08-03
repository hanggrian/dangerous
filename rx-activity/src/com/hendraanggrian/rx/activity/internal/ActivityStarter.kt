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
internal interface ActivityStarter {

    fun getContext(): Context

    @RequiresApi(16)
    @TargetApi(16)
    fun startActivityForResult(intent: Intent, requestCode: Int, options: Bundle?)

    fun startActivityForResult(intent: Intent, requestCode: Int)
}

internal fun Activity.toActivityStarter() = object : ActivityStarter {

    override fun getContext() = this@toActivityStarter

    @RequiresApi(16)
    @TargetApi(16)
    override fun startActivityForResult(intent: Intent, requestCode: Int, options: Bundle?) = this@toActivityStarter.startActivityForResult(intent, requestCode, options)

    override fun startActivityForResult(intent: Intent, requestCode: Int) = this@toActivityStarter.startActivityForResult(intent, requestCode)
}

internal fun Fragment.toActivityStarter() = object : ActivityStarter {

    override fun getContext() = if (Build.VERSION.SDK_INT >= 23) context else activity

    @RequiresApi(16)
    @TargetApi(16)
    override fun startActivityForResult(intent: Intent, requestCode: Int, options: Bundle?) = this@toActivityStarter.startActivityForResult(intent, requestCode, options)

    override fun startActivityForResult(intent: Intent, requestCode: Int) = this@toActivityStarter.startActivityForResult(intent, requestCode)
}

internal fun android.support.v4.app.Fragment.toActivityStarter() = object : ActivityStarter {

    override fun getContext() = context

    @RequiresApi(16)
    @TargetApi(16)
    override fun startActivityForResult(intent: Intent, requestCode: Int, options: Bundle?) = this@toActivityStarter.startActivityForResult(intent, requestCode, options)

    override fun startActivityForResult(intent: Intent, requestCode: Int) = this@toActivityStarter.startActivityForResult(intent, requestCode)
}