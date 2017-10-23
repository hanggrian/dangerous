package com.hendraanggrian.app

import android.app.Activity
import android.app.Fragment
import android.content.Intent

fun <T : Activity> T.startActivityForResult2(
        intent: Intent,
        block: T.(requestCode: Int, resultCode: Int, data: Intent?) -> Unit
) {
    val requestCode = nextRequestCode
    append(requestCode, block)
    startActivityForResult(intent, requestCode)
}

fun <T : Fragment> T.startActivityForResult2(
        intent: Intent,
        block: T.(requestCode: Int, resultCode: Int, data: Intent?) -> Unit
) {
    val requestCode = nextRequestCode
    append(requestCode, block)
    startActivityForResult(intent, requestCode)
}

fun <T : android.support.v4.app.Fragment> T.startActivityForResult2(
        intent: Intent,
        block: T.(requestCode: Int, resultCode: Int, data: Intent?) -> Unit
) {
    val requestCode = nextRequestCode
    append(requestCode, block)
    startActivityForResult(intent, requestCode)
}