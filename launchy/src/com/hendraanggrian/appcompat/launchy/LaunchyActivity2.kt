@file:JvmMultifileClass
@file:JvmName("LaunchyKt")
@file:Suppress("DEPRECATION", "NOTHING_TO_INLINE", "UNUSED")

package com.hendraanggrian.appcompat.launchy

import android.app.Activity
import android.app.Fragment
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import androidx.annotation.RequiresApi

/**
 * Same as [launchActivity] but will only trigger [callback] if result matches expected.
 *
 * @param intent the intent to start.
 * @param callback activity result callback.
 *
 * @throws ActivityNotFoundException if no activity is found that can handle the [intent].
 *
 * @see Activity.startActivityForResult
 */
inline fun <T : Activity> T.launchActivity(
    expectedResultCode: Int,
    intent: Intent,
    noinline callback: T.(data: Intent?) -> Unit
) = launchActivity(intent) { resultCode, data ->
    if (resultCode == expectedResultCode) callback(data)
}

/**
 * Same as [launchActivity] but will only trigger [callback] if result matches expected.
 *
 * @param intent the intent to start.
 * @param options additional options for how the activity should be started.
 * @param callback activity result callback.
 *
 * @throws ActivityNotFoundException if no activity is found that can handle the [intent].
 *
 * @see Activity.startActivityForResult
 */
@RequiresApi(16)
inline fun <T : Activity> T.launchActivity(
    expectedResultCode: Int,
    intent: Intent,
    options: Bundle,
    noinline callback: T.(data: Intent?) -> Unit
) = launchActivity(intent, options) { resultCode, data ->
    if (resultCode == expectedResultCode) callback(data)
}

/**
 * Same as [launchActivity] but will only trigger [callback] if result matches expected.
 *
 * @param intent the intent to start.
 * @param callback activity result callback.
 *
 * @throws ActivityNotFoundException if no activity is found that can handle the [intent].
 *
 * @see Fragment.startActivityForResult
 */
inline fun <T : Fragment> T.launchActivity(
    expectedResultCode: Int,
    intent: Intent,
    noinline callback: T.(data: Intent?) -> Unit
) = launchActivity(intent) { resultCode, data ->
    if (resultCode == expectedResultCode) callback(data)
}

/**
 * Same as [launchActivity] but will only trigger [callback] if result matches expected.
 *
 * @param intent the intent to start.
 * @param options additional options for how the activity should be started.
 * @param callback activity result callback.
 *
 * @throws ActivityNotFoundException if no activity is found that can handle the [intent].
 *
 * @see android.support.v4.app.Fragment.startActivityForResult
 */
@RequiresApi(16)
inline fun <T : Fragment> T.launchActivity(
    expectedResultCode: Int,
    intent: Intent,
    options: Bundle,
    noinline callback: T.(data: Intent?) -> Unit
) = launchActivity(intent, options) { resultCode, data ->
    if (resultCode == expectedResultCode) callback(data)
}

/**
 * Same as [launchActivity] but will only trigger [callback] if result matches expected.
 *
 * @param intent the intent to start.
 * @param callback activity result callback.
 *
 * @throws ActivityNotFoundException if no activity is found that can handle the [intent].
 *
 * @see android.support.v4.app.Fragment.startActivityForResult
 */
inline fun <T : androidx.fragment.app.Fragment> T.launchActivity(
    expectedResultCode: Int,
    intent: Intent,
    noinline callback: T.(data: Intent?) -> Unit
) = launchActivity(intent) { resultCode, data ->
    if (resultCode == expectedResultCode) callback(data)
}

/**
 * Same as [launchActivity] but will only trigger [callback] if result matches expected.
 *
 * @param intent the intent to start.
 * @param options additional options for how the activity should be started.
 * @param callback activity result callback.
 *
 * @throws ActivityNotFoundException if no activity is found that can handle the [intent].
 *
 * @see android.support.v4.app.Fragment.startActivityForResult
 */
@RequiresApi(16)
inline fun <T : androidx.fragment.app.Fragment> T.launchActivity(
    expectedResultCode: Int,
    intent: Intent,
    options: Bundle,
    noinline callback: T.(data: Intent?) -> Unit
) = launchActivity(intent, options) { resultCode, data ->
    if (resultCode == expectedResultCode) callback(data)
}
