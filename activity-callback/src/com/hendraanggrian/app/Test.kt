package com.hendraanggrian.app

import android.app.Activity
import android.content.Intent
import android.support.v4.app.Fragment

fun <T : Activity> T.startActivityForResult2(
        intent: Intent,
        result: Int = Activity.RESULT_OK,
        block: T.(requestCode: Int, resultCode: Int, data: Intent?) -> Unit,
        fallback: T.(requestCode: Int, resultCode: Int, data: Intent?) -> Unit
) {
    val requestCode = nextRequestCode
    append(requestCode, object : ActivityStarter<T> {
        override val resultCode: Int get() = result
        override val block: T.(requestCode: Int, resultCode: Int, data: Intent?) -> Unit get() = block
        override val fallback: T.(requestCode: Int, resultCode: Int, data: Intent?) -> Unit get() = fallback
    })
    startActivityForResult(intent, requestCode)
}

fun Fragment.startActivityForResult2(
        intent: Intent,
        result: Int = Activity.RESULT_OK,
        block: Fragment.(requestCode: Int, resultCode: Int, data: Intent?) -> Unit,
        fallback: Fragment.(requestCode: Int, resultCode: Int, data: Intent?) -> Unit
) {
    val requestCode = nextRequestCode
    append(requestCode, object : ActivityStarter<Fragment> {
        override val resultCode: Int get() = result
        override val block: Fragment.(requestCode: Int, resultCode: Int, data: Intent?) -> Unit get() = block
        override val fallback: Fragment.(requestCode: Int, resultCode: Int, data: Intent?) -> Unit get() = fallback
    })
    startActivityForResult(intent, requestCode)
}