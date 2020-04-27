@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.appcompat.launchy.internal

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.PermissionChecker

@PublishedApi
@RequiresApi(Build.VERSION_CODES.M)
internal inline fun Context.isAllGranted(vararg permissions: String): Boolean = permissions
    .all { PermissionChecker.checkSelfPermission(this, it) == PermissionChecker.PERMISSION_GRANTED }
