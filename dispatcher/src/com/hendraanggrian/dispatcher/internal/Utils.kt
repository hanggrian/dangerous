@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.dispatcher.internal

import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.support.v4.content.PermissionChecker.checkSelfPermission
import android.support.v4.util.SparseArrayCompat

internal inline fun <E> SparseArrayCompat<E>.containsKey(key: Int): Boolean = indexOfKey(key) > -1

@PublishedApi
internal inline fun Context.isAllGranted(vararg permissions: String): Boolean = permissions
    .all { checkSelfPermission(this, it) == PERMISSION_GRANTED }