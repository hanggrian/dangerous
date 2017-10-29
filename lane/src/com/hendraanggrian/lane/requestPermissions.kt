@file:JvmMultifileClass
@file:JvmName("LanesKt")
@file:Suppress("NOTHING_TO_INLINE", "UNUSED")

package com.hendraanggrian.lane

import android.app.Activity
import android.app.Fragment
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M

/**
 * Request permissions with auto-generated request code.
 * The result will then have to be handled in [callback] instead of [Activity.onRequestPermissionsResult].
 *
 * @param permissions The permissions to request.
 * @param callback Permission result callback.
 *
 * @see Activity.requestPermissions
 */
inline fun <reified T : Activity> T.requestPermissions(
        vararg permissions: String,
        noinline callback: T.(Boolean) -> Unit
) = if (SDK_INT < M || isSelfPermissionsGranted(*permissions)) callback.invoke(this, true)
else requestPermissions(permissions, appendPermissionCallback(callback))

/**
 * Request permissions with auto-generated request code.
 * The result will then have to be handled in [callback] instead of [Fragment.onRequestPermissionsResult].
 *
 * @param permissions The permissions to request.
 * @param callback Permission result callback.
 *
 * @see Fragment.requestPermissions
 */
inline fun <reified T : Fragment> T.requestPermissions(
        vararg permissions: String,
        noinline callback: T.(Boolean) -> Unit
) = if (SDK_INT < M || activity.isSelfPermissionsGranted(*permissions)) callback.invoke(this, true)
else requestPermissions(permissions, appendPermissionCallback(callback))

/**
 * Request permissions with auto-generated request code.
 * The result will then have to be handled in [callback] instead of [android.support.v4.app.Fragment.onRequestPermissionsResult].
 *
 * @param permissions The permissions to request.
 * @param callback Permission result callback.
 *
 * @see android.support.v4.app.Fragment.requestPermissions
 */
inline fun <reified T : android.support.v4.app.Fragment> T.requestPermissions(
        vararg permissions: String,
        noinline callback: T.(Boolean) -> Unit
) = if (SDK_INT < M || context.isSelfPermissionsGranted(*permissions)) callback.invoke(this, true)
else requestPermissions(permissions, appendPermissionCallback(callback))