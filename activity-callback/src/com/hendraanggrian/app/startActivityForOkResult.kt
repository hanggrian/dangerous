@file:JvmMultifileClass
@file:JvmName("ActivityCallbacks")
@file:Suppress("NOTHING_TO_INLINE", "UNUSED")

package com.hendraanggrian.app

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent

inline fun <T : Activity> T.startActivityForOkResult(
        intent: Intent,
        noinline callback: T.(data: Intent?) -> Unit
) = startActivityForResult(intent, { resultCode, data ->
    if (resultCode == RESULT_OK) callback(data)
})