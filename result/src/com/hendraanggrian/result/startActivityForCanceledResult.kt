@file:JvmMultifileClass
@file:JvmName("ActivityResultKt")
@file:Suppress("NOTHING_TO_INLINE", "UNUSED")

package com.hendraanggrian.result

import android.app.Activity
import android.app.Activity.RESULT_CANCELED
import android.app.Fragment
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.support.annotation.RequiresApi

/**
 * Same as [startActivityForResult] but will only trigger [callback] if result is [Activity.RESULT_CANCELED].
 *
 * @param intent The intent to start.
 * @param callback Activity result callback.
 *
 * @throws ActivityNotFoundException if no activity is found that can handle the [intent].
 *
 * @see Activity.startActivityForResult
 */
inline fun <reified T : Activity> T.startActivityForCanceledResult(
        intent: Intent,
        noinline callback: T.(data: Intent?) -> Unit
) = startActivityForResult(intent, { resultCode, data ->
    if (resultCode == RESULT_CANCELED) callback(data)
})

/**
 * Same as [startActivityForResult] but will only trigger [callback] if result is [Activity.RESULT_CANCELED].
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
inline fun <reified T : Activity> T.startActivityForCanceledResult(
        intent: Intent,
        options: Bundle,
        noinline callback: T.(data: Intent?) -> Unit
) = startActivityForResult(intent, options, { resultCode, data ->
    if (resultCode == RESULT_CANCELED) callback(data)
})

/**
 * Same as [startActivityForResult] but will only trigger [callback] if result is [Activity.RESULT_CANCELED].
 *
 * @param intent The intent to start.
 * @param callback Activity result callback.
 *
 * @throws ActivityNotFoundException if no activity is found that can handle the [intent].
 *
 * @see Fragment.startActivityForResult
 */
inline fun <reified T : Fragment> T.startActivityForCanceledResult(
        intent: Intent,
        noinline callback: T.(data: Intent?) -> Unit
) = startActivityForResult(intent, { resultCode, data ->
    if (resultCode == RESULT_CANCELED) callback(data)
})

/**
 * Same as [startActivityForResult] but will only trigger [callback] if result is [Activity.RESULT_CANCELED].
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
inline fun <reified T : Fragment> T.startActivityForCanceledResult(
        intent: Intent,
        options: Bundle,
        noinline callback: T.(data: Intent?) -> Unit
) = startActivityForResult(intent, options, { resultCode, data ->
    if (resultCode == RESULT_CANCELED) callback(data)
})

/**
 * Same as [startActivityForResult] but will only trigger [callback] if result is [Activity.RESULT_CANCELED].
 *
 * @param intent The intent to start.
 * @param callback Activity result callback.
 *
 * @throws ActivityNotFoundException if no activity is found that can handle the [intent].
 *
 * @see android.support.v4.app.Fragment.startActivityForResult
 */
inline fun <reified T : android.support.v4.app.Fragment> T.startActivityForCanceledResult(
        intent: Intent,
        noinline callback: T.(data: Intent?) -> Unit
) = startActivityForResult(intent, { resultCode, data ->
    if (resultCode == RESULT_CANCELED) callback(data)
})

/**
 * Same as [startActivityForResult] but will only trigger [callback] if result is [Activity.RESULT_CANCELED].
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
inline fun <reified T : android.support.v4.app.Fragment> T.startActivityForCanceledResult(
        intent: Intent,
        options: Bundle,
        noinline callback: T.(data: Intent?) -> Unit
) = startActivityForResult(intent, options, { resultCode, data ->
    if (resultCode == RESULT_CANCELED) callback(data)
})