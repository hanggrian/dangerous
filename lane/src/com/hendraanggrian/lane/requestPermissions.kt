@file:JvmMultifileClass
@file:JvmName("LanesKt")
@file:Suppress("NOTHING_TO_INLINE", "UNUSED")

package com.hendraanggrian.lane

import android.app.Activity
import android.os.Build

inline fun <T : Activity> T.requestPermissions(
        vararg permissions: String,
        noinline callback: T.(Boolean) -> Unit
) = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) callback.invoke(this, true)
else requestPermissions(permissions, appendPermissionCallback(callback))