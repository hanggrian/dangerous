package com.hendraanggrian.appcompat.dangerous

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

/**
 * Returns true if single permission is allowed.
 *
 * @param permission single [android.Manifest.permission].
 */
inline fun Context.hasPermission(permission: String): Boolean =
    ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

/**
 * Returns true if multiple permissions are allowed.
 *
 * @param permissions multiple [android.Manifest.permission], cannot be empty.
 */
fun Context.hasPermissions(vararg permissions: String): Boolean {
    check(permissions.isNotEmpty()) { "Empty permissions." }
    return permissions.all { hasPermission(it) }
}

/**
 * Returns true if multiple permissions are allowed.
 *
 * @param permissions multiple [android.Manifest.permission], cannot be empty.
 */
fun Context.hasPermissions(permissions: Collection<String>): Boolean {
    check(permissions.isNotEmpty()) { "Empty permissions." }
    return permissions.all { hasPermission(it) }
}
