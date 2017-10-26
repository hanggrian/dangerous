@file:JvmMultifileClass
@file:JvmName("ActivityResultsKt")
@file:Suppress("NOTHING_TO_INLINE", "UNUSED")

package com.hendraanggrian.app

import android.app.Activity
import android.app.Fragment
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.support.annotation.RequiresApi

/**
 * Start an activity for result with random request code.
 *
 * @param intent The intent to start.
 * @param callback Activity result callback.
 *
 * @throws ActivityNotFoundException if no activity is found that can handle the [intent].
 *
 * @see Activity.startActivityForResult
 */
inline fun <T : Activity> T.startActivityForResult(
        intent: Intent,
        noinline callback: T.(resultCode: Int, data: Intent?) -> Unit
) {
    val requestCode = generateRequestCode()
    appendCallback(requestCode, callback)
    startActivityForResult(intent, requestCode)
}

/**
 * Start an activity for result with random request code.
 *
 * @param intent The intent to start.
 * @param options Additional options for how the activity should be started.
 * @param callback Activity result callback.
 *
 * @throws ActivityNotFoundException if no activity is found that can handle the [intent].
 *
 * @see Activity.startActivityForResult
 */
@RequiresApi(16)
inline fun <T : Activity> T.startActivityForResult(
        intent: Intent,
        options: Bundle,
        noinline callback: T.(resultCode: Int, data: Intent?) -> Unit
) {
    val requestCode = generateRequestCode()
    appendCallback(requestCode, callback)
    startActivityForResult(intent, requestCode, options)
}

/**
 * Start an activity for result with random request code.
 *
 * @param intent The intent to start.
 * @param callback Activity result callback.
 *
 * @throws ActivityNotFoundException if no activity is found that can handle the [intent].
 *
 * @see Fragment.startActivityForResult
 */
inline fun <T : Fragment> T.startActivityForResult(
        intent: Intent,
        noinline callback: T.(resultCode: Int, data: Intent?) -> Unit
) {
    val requestCode = generateRequestCode()
    appendCallback(requestCode, callback)
    startActivityForResult(intent, requestCode)
}

/**
 * Start an activity for result with random request code.
 *
 * @param intent The intent to start.
 * @param options Additional options for how the activity should be started.
 * @param callback Activity result callback.
 *
 * @throws ActivityNotFoundException if no activity is found that can handle the [intent].
 *
 * @see Fragment.startActivityForResult
 */
@RequiresApi(16)
inline fun <T : Fragment> T.startActivityForResult(
        intent: Intent,
        options: Bundle,
        noinline callback: T.(resultCode: Int, data: Intent?) -> Unit
) {
    val requestCode = generateRequestCode()
    appendCallback(requestCode, callback)
    startActivityForResult(intent, requestCode, options)
}

/**
 * Start an activity for result with random request code.
 *
 * @param intent The intent to start.
 * @param callback Activity result callback.
 *
 * @throws ActivityNotFoundException if no activity is found that can handle the [intent].
 *
 * @see android.support.v4.app.Fragment.startActivityForResult
 */
inline fun <T : android.support.v4.app.Fragment> T.startActivityForResult(
        intent: Intent,
        noinline callback: T.(resultCode: Int, data: Intent?) -> Unit
) {
    val requestCode = generateRequestCode()
    appendCallback(requestCode, callback)
    startActivityForResult(intent, requestCode)
}

/**
 * Start an activity for result with random request code.
 *
 * @param intent The intent to start.
 * @param options Additional options for how the activity should be started.
 * @param callback Activity result callback.
 *
 * @throws ActivityNotFoundException if no activity is found that can handle the [intent].
 *
 * @see android.support.v4.app.Fragment.startActivityForResult
 */
@RequiresApi(16)
inline fun <T : android.support.v4.app.Fragment> T.startActivityForResult(
        intent: Intent,
        options: Bundle,
        noinline callback: T.(resultCode: Int, data: Intent?) -> Unit
) {
    val requestCode = generateRequestCode()
    appendCallback(requestCode, callback)
    startActivityForResult(intent, requestCode, options)
}