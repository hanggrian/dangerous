@file:JvmMultifileClass
@file:JvmName("LaunchyKt")
@file:Suppress("DEPRECATION")

package com.hendraanggrian.appcompat.launchy

import android.app.Activity
import android.app.Fragment
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker

/**
 * Request permissions with auto-generated request code.
 * The result will then have to be handled in [callback]
 * instead of [Activity.onRequestPermissionsResult].
 *
 * @param permissions the permissions to request.
 * @param callback permission result callback.
 *
 * @see Activity.launchPermission
 */
@Suppress("UNCHECKED_CAST")
fun <T : Activity> Activity.launchPermission(
    vararg permissions: String,
    callback: T.(isGranted: Boolean) -> Unit
) {
    val sb = StringBuilder("launchPermission: ")
    if (Build.VERSION.SDK_INT < 23) {
        Launchy.debug(sb.append("passed (pre-M)"))
        callback.invoke(this as T, true)
        return
    }
    when {
        permissions.all { PermissionChecker.checkSelfPermission(this, it) == PermissionChecker.PERMISSION_GRANTED } -> {
            Launchy.debug(sb.append("passed (already granted)"))
            callback.invoke(this as T, true)
        }
        else -> ActivityCompat.requestPermissions(this, permissions, Launchy.appendPermission(callback))
    }
}

/**
 * Request permissions with auto-generated request code.
 * The result will then have to be handled in [callback]
 * instead of [Fragment.onRequestPermissionsResult].
 *
 * @param permissions the permissions to request.
 * @param callback permission result callback.
 *
 * @see Fragment.launchPermission
 */
@Suppress("NOTHING_TO_INLINE")
inline fun <T : Activity> Fragment.launchPermission(
    vararg permissions: String,
    noinline callback: T.(isGranted: Boolean) -> Unit
): Unit = activity.launchPermission(*permissions, callback = callback)

/**
 * Request permissions with auto-generated request code.
 * The result will then have to be handled in [callback]
 * instead of [android.support.v4.app.Fragment.onRequestPermissionsResult].
 *
 * @param permissions the permissions to request.
 * @param callback permission result callback.
 *
 * @see android.support.v4.app.Fragment.requestPermissions
 */
@Suppress("NOTHING_TO_INLINE")
inline fun <T : Activity> androidx.fragment.app.Fragment.launchPermission(
    vararg permissions: String,
    noinline callback: T.(isGranted: Boolean) -> Unit
): Unit = activity!!.launchPermission(*permissions, callback = callback)
