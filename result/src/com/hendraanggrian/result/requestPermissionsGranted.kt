@file:JvmMultifileClass
@file:JvmName("PermissionsResultKt")
@file:Suppress("NOTHING_TO_INLINE", "UNUSED")

package com.hendraanggrian.result

import android.app.Activity
import android.app.Fragment

/**
 * Same as [requestPermissions] but will only trigger [callback] if result is all [permissions] are granted.
 *
 * @param permissions The permissions to request.
 * @param callback Permission result callback.
 *
 * @see Activity.requestPermissions
 */
inline fun <reified T : Activity> T.requestPermissionsGranted(
        vararg permissions: String,
        noinline callback: T.() -> Unit
) = requestPermissions(*permissions) { granted ->
    if (granted) callback()
}

/**
 * Same as [requestPermissions] but will only trigger [callback] if result is all [permissions] are granted.
 *
 * @param permissions The permissions to request.
 * @param callback Permission result callback.
 *
 * @see Fragment.requestPermissions
 */
inline fun <reified T : Fragment> T.requestPermissionsGranted(
        vararg permissions: String,
        noinline callback: T.() -> Unit
) = requestPermissions(*permissions) { granted ->
    if (granted) callback()
}

/**
 * Same as [requestPermissions] but will only trigger [callback] if result is all [permissions] are granted.
 *
 * @param permissions The permissions to request.
 * @param callback Permission result callback.
 *
 * @see android.support.v4.app.Fragment.requestPermissions
 */
inline fun <reified T : android.support.v4.app.Fragment> T.requestPermissionsGranted(
        vararg permissions: String,
        noinline callback: T.() -> Unit
) = requestPermissions(*permissions) { granted ->
    if (granted) callback()
}