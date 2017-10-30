@file:JvmMultifileClass
@file:JvmName("PermissionsResultKt")
@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")

package com.hendraanggrian.result

import android.app.Activity
import android.app.Fragment
import android.content.pm.PackageManager

/** Redirect [Activity.onRequestPermissionsResult] so that it may be triggered on [requestPermissions]. */
inline fun <reified T : Activity> T.onRequestPermissionsResult2(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
) = Result.getCallback<T.(Boolean) -> Unit>(requestCode)?.invoke(this, grantResults
        .all { it == PackageManager.PERMISSION_GRANTED })

/** Redirect [Fragment.onRequestPermissionsResult] so that it may be triggered on [requestPermissions]. */
inline fun <reified T : Fragment> T.onRequestPermissionsResult2(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
) = Result.getCallback<T.(Boolean) -> Unit>(requestCode)?.invoke(this, grantResults
        .all { it == PackageManager.PERMISSION_GRANTED })

/** Redirect [android.support.v4.app.Fragment.onRequestPermissionsResult] so that it may be triggered on [requestPermissions]. */
inline fun <reified T : android.support.v4.app.Fragment> T.onRequestPermissionsResult2(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
) = Result.getCallback<T.(Boolean) -> Unit>(requestCode)?.invoke(this, grantResults
        .all { it == PackageManager.PERMISSION_GRANTED })