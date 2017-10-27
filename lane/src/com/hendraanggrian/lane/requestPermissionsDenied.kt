@file:JvmMultifileClass
@file:JvmName("LanesKt")
@file:Suppress("NOTHING_TO_INLINE", "UNUSED")

package com.hendraanggrian.lane

import android.app.Activity
import android.app.Fragment

inline fun <T : Activity> T.requestPermissionsDenied(
        vararg permissions: String,
        noinline callback: T.() -> Unit
) = requestPermissions(*permissions) { granted ->
    if (!granted) callback()
}

inline fun <T : Fragment> T.requestPermissionsDenied(
        vararg permissions: String,
        noinline callback: T.() -> Unit
) = requestPermissions(*permissions) { granted ->
    if (!granted) callback()
}

inline fun <T : android.support.v4.app.Fragment> T.requestPermissionsDenied(
        vararg permissions: String,
        noinline callback: T.() -> Unit
) = requestPermissions(*permissions) { granted ->
    if (!granted) callback()
}