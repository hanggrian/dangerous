@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.lane

import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.content.PermissionChecker
import android.support.v4.util.SparseArrayCompat

internal inline fun <E> SparseArrayCompat<E>.containsKey(key: Int): Boolean = indexOfKey(key) > -1

@PublishedApi
internal inline fun Context.isSelfPermissionsGranted(vararg permissions: String): Boolean = permissions
        .all { PermissionChecker.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED }