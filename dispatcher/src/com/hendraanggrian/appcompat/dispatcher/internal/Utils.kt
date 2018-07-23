@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.appcompat.dispatcher.internal

import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.PermissionChecker

@PublishedApi
@RequiresApi(Build.VERSION_CODES.M)
internal inline fun Context.isAllGranted(vararg permissions: String): Boolean = permissions
    .all { PermissionChecker.checkSelfPermission(this, it) == PERMISSION_GRANTED }