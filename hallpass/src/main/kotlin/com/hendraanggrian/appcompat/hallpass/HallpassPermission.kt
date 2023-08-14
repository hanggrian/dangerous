package com.hendraanggrian.appcompat.hallpass

import android.app.Activity
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED

/**
 * Request permissions with auto-generated request code.
 * The result will then have to be handled in [callback]
 * instead of [Activity.onRequestPermissionsResult].
 *
 * @param permissions the permissions to request.
 * @param callback permission result callback.
 */
fun <T : Activity> T.requestPermissions(
    vararg permissions: String,
    callback: (isGranted: Boolean) -> Unit
) {
    val sb = StringBuilder("launchPermission: ")
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        Hallpass.debug(sb.append("passed (pre-M)"))
        return callback(true)
    }
    if (permissions.all { PermissionChecker.checkSelfPermission(this, it) == PERMISSION_GRANTED }) {
        Hallpass.debug(sb.append("passed (already granted)"))
        return callback(true)
    }
    ActivityCompat.requestPermissions(this, permissions, Hallpass.appendPermission(callback))
}

inline fun <T : Activity> T.requestPermissions(
    vararg permissions: String,
    crossinline onGranted: () -> Unit,
    crossinline onDenied: () -> Unit
): Unit = requestPermissions(*permissions) { isGranted ->
    when {
        isGranted -> onGranted()
        else -> onDenied()
    }
}
