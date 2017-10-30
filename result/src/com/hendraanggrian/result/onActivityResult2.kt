@file:JvmMultifileClass
@file:JvmName("ActivityResultKt")
@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")

package com.hendraanggrian.result

import android.app.Activity
import android.app.Fragment
import android.content.Intent

/** Redirect [Activity.onActivityResult] so that it may be triggered on [startActivityForResult]. */
inline fun <reified T : Activity> T.onActivityResult2(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
) = Result.getCallback<T.(Int, Intent?) -> Unit>(requestCode)?.invoke(this, resultCode, data)

/** Redirect [Fragment.onActivityResult] so that it may be triggered on [startActivityForResult]. */
inline fun <reified T : Fragment> T.onActivityResult2(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
) = Result.getCallback<T.(Int, Intent?) -> Unit>(requestCode)?.invoke(this, resultCode, data)

/** Redirect [android.support.v4.app.Fragment.onActivityResult] so that it may be triggered on [startActivityForResult]. */
inline fun <reified T : android.support.v4.app.Fragment> T.onActivityResult2(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
) = Result.getCallback<T.(Int, Intent?) -> Unit>(requestCode)?.invoke(this, resultCode, data)