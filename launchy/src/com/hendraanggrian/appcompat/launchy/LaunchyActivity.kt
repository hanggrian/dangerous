@file:JvmMultifileClass
@file:JvmName("LaunchyKt")
@file:Suppress("DEPRECATION", "UNUSED")

package com.hendraanggrian.appcompat.launchy

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Fragment
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import androidx.annotation.RequiresApi

/**
 * Start an activity for result with auto-generated request code.
 * The result will then have to be handled in [callback]
 * instead of [Activity.onActivityResult].
 *
 * @param intent the intent to start.
 * @param callback activity result callback.
 *
 * @throws ActivityNotFoundException if no activity is found that can handle the [intent].
 *
 * @see Activity.startActivityForResult
 */
fun <T : Activity> T.launchActivity(
    intent: Intent,
    callback: T.(resultCode: Int, data: Intent?) -> Unit
) = startActivityForResult(intent, Launchy.appendActivity(callback))

/**
 * Start an activity for result with auto-generated request code.
 * The result will then have to be handled in [callback]
 * instead of [Activity.onActivityResult].
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
@SuppressLint("NewApi")
fun <T : Activity> T.launchActivity(
    intent: Intent,
    options: Bundle,
    callback: T.(resultCode: Int, data: Intent?) -> Unit
) = startActivityForResult(intent, Launchy.appendActivity(callback), options)

/**
 * Start an activity for result with auto-generated request code.
 * The result will then have to be handled in [callback]
 * instead of [Fragment.onActivityResult].
 *
 * @param intent the intent to start.
 * @param callback activity result callback.
 *
 * @throws ActivityNotFoundException if no activity is found that can handle the [intent].
 *
 * @see Fragment.startActivityForResult
 */
fun <T : Fragment> T.launchActivity(
    intent: Intent,
    callback: T.(resultCode: Int, data: Intent?) -> Unit
) = startActivityForResult(intent, Launchy.appendActivity(callback))

/**
 * Start an activity for result with auto-generated request code.
 * The result will then have to be handled in [callback]
 * instead of [Fragment.onActivityResult].
 *
 * @param intent the intent to start.
 * @param options additional options for how the activity should be started.
 * @param callback activity result callback.
 *
 * @throws ActivityNotFoundException if no activity is found that can handle the [intent].
 *
 * @see Fragment.startActivityForResult
 */
@RequiresApi(16)
@SuppressLint("NewApi")
fun <T : Fragment> T.launchActivity(
    intent: Intent,
    options: Bundle,
    callback: T.(resultCode: Int, data: Intent?) -> Unit
) = startActivityForResult(intent, Launchy.appendActivity(callback), options)

/**
 * Start an activity for result with auto-generated request code.
 * The result will then have to be handled in [callback]
 * instead of [android.support.v4.app.Fragment.onActivityResult].
 *
 * @param intent the intent to start.
 * @param callback activity result callback.
 *
 * @throws ActivityNotFoundException if no activity is found that can handle the [intent].
 *
 * @see android.support.v4.app.Fragment.startActivityForResult
 */
fun <T : androidx.fragment.app.Fragment> T.launchActivity(
    intent: Intent,
    callback: T.(resultCode: Int, data: Intent?) -> Unit
) = startActivityForResult(intent, Launchy.appendActivity(callback))

/**
 * Start an activity for result with auto-generated request code.
 * The result will then have to be handled in [callback]
 * instead of [android.support.v4.app.Fragment.onActivityResult].
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
fun <T : androidx.fragment.app.Fragment> T.launchActivity(
    intent: Intent,
    options: Bundle,
    callback: T.(resultCode: Int, data: Intent?) -> Unit
) = startActivityForResult(intent, Launchy.appendActivity(callback), options)
