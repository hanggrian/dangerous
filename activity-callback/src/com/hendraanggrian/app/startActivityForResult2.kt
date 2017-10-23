@file:Suppress("NOTHING_TO_INLINE", "UNUSED")

package com.hendraanggrian.app

import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.support.annotation.RequiresApi

inline fun <T : Activity> T.startActivityForResult2(
        intent: Intent,
        noinline callback: T.(requestCode: Int, resultCode: Int, data: Intent?) -> Unit
) {
    val requestCode = nextRequestCode
    queue(requestCode, callback)
    startActivityForResult(intent, requestCode)
}

inline @RequiresApi(16)
fun <T : Activity> T.startActivityForResult2(
        intent: Intent,
        options: Bundle?,
        noinline callback: T.(requestCode: Int, resultCode: Int, data: Intent?) -> Unit
) {
    val requestCode = nextRequestCode
    queue(requestCode, callback)
    startActivityForResult(intent, requestCode, options)
}

inline fun <T : Fragment> T.startActivityForResult2(
        intent: Intent,
        noinline callback: T.(requestCode: Int, resultCode: Int, data: Intent?) -> Unit
) {
    val requestCode = nextRequestCode
    queue(requestCode, callback)
    startActivityForResult(intent, requestCode)
}

inline @RequiresApi(16)
fun <T : Fragment> T.startActivityForResult2(
        intent: Intent,
        options: Bundle?,
        noinline callback: T.(requestCode: Int, resultCode: Int, data: Intent?) -> Unit
) {
    val requestCode = nextRequestCode
    queue(requestCode, callback)
    startActivityForResult(intent, requestCode, options)
}

inline fun <T : android.support.v4.app.Fragment> T.startActivityForResult2(
        intent: Intent,
        noinline callback: T.(requestCode: Int, resultCode: Int, data: Intent?) -> Unit
) {
    val requestCode = nextRequestCode
    queue(requestCode, callback)
    startActivityForResult(intent, requestCode)
}

@RequiresApi(16)
inline fun <T : android.support.v4.app.Fragment> T.startActivityForResult2(
        intent: Intent,
        options: Bundle?,
        noinline callback: T.(requestCode: Int, resultCode: Int, data: Intent?) -> Unit
) {
    val requestCode = nextRequestCode
    queue(requestCode, callback)
    startActivityForResult(intent, requestCode, options)
}