@file:JvmMultifileClass
@file:JvmName("LanesKt")
@file:Suppress("NOTHING_TO_INLINE", "UNUSED")

package com.hendraanggrian.lane

import android.app.Activity
import android.app.Fragment
import android.os.Build

inline fun <T : Activity> T.requestPermissions(
        vararg permissions: String,
        noinline callback: T.(Boolean) -> Unit
) = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || isSelfPermissionsGranted(*permissions)) callback.invoke(this, true)
else requestPermissions(permissions, appendPermissionCallback(callback))

inline fun <T : Fragment> T.requestPermissions(
        vararg permissions: String,
        noinline callback: T.(Boolean) -> Unit
) = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || activity.isSelfPermissionsGranted(*permissions)) callback.invoke(this, true)
else requestPermissions(permissions, appendPermissionCallback(callback))

inline fun <T : android.support.v4.app.Fragment> T.requestPermissions(
        vararg permissions: String,
        noinline callback: T.(Boolean) -> Unit
) = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || context.isSelfPermissionsGranted(*permissions)) callback.invoke(this, true)
else requestPermissions(permissions, appendPermissionCallback(callback))