@file:JvmMultifileClass
@file:JvmName("LanesKt")
@file:Suppress("NOTHING_TO_INLINE", "UNUSED")

package com.hendraanggrian.lane

import android.app.Activity
import android.app.Fragment

/**
 * Same as [requestPermissions] but will only trigger [callback] if result is any [permissions] are denied.
 *
 * @param permissions The permissions to request.
 * @param callback Permission result callback.
 *
 * @see Activity.requestPermissions
 */
inline fun <reified T : Activity> T.requestPermissionsDenied(
        vararg permissions: String,
        noinline callback: T.() -> Unit
) = requestPermissions(*permissions) { granted ->
    if (!granted) callback()
}

/**
 * Same as [requestPermissions] but will only trigger [callback] if result is any [permissions] are denied.
 *
 * @param permissions The permissions to request.
 * @param callback Permission result callback.
 *
 * @see Fragment.requestPermissions
 */
inline fun <reified T : Fragment> T.requestPermissionsDenied(
        vararg permissions: String,
        noinline callback: T.() -> Unit
) = requestPermissions(*permissions) { granted ->
    if (!granted) callback()
}

/**
 * Same as [requestPermissions] but will only trigger [callback] if result is any [permissions] are denied.
 *
 * @param permissions The permissions to request.
 * @param callback Permission result callback.
 *
 * @see android.support.v4.app.Fragment.requestPermissions
 */
inline fun <reified T : android.support.v4.app.Fragment> T.requestPermissionsDenied(
        vararg permissions: String,
        noinline callback: T.() -> Unit
) = requestPermissions(*permissions) { granted ->
    if (!granted) callback()
}