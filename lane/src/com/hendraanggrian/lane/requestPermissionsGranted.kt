@file:JvmMultifileClass
@file:JvmName("LanesKt")
@file:Suppress("NOTHING_TO_INLINE", "UNUSED")

package com.hendraanggrian.lane

import android.app.Activity

inline fun <T : Activity> T.requestPermissionsGranted(
        vararg permissions: String,
        noinline callback: T.() -> Unit
) = requestPermissions(*permissions) { granted ->
    if (granted) callback()
}