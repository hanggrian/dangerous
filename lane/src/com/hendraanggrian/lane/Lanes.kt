@file:JvmMultifileClass
@file:JvmName("LanesKt")
@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")

package com.hendraanggrian.lane

import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v4.util.SparseArrayCompat
import java.lang.ref.WeakReference
import java.util.*

/** Weak reference of Random to generate random number. */
private var RANDOM: WeakReference<Random>? = null

/**
 * Queued callbacks that will be invoked one-by-one on activity result.
 * Once invoked, callback will be removed from this collection.
 */
@PublishedApi internal val CALLBACKS: SparseArrayCompat<Any> = SparseArrayCompat()

/**
 * Attempt to get [Random] instance from [WeakReference].
 * When no instance is found, create a new one and save it.
 * Then generate a random number that is guaranteed to be non-duplicate of [CALLBACKS] key.
 */
private fun generateRequestCode(bound: Int): Int {
    var random = RANDOM?.get()
    if (random == null) {
        random = Random()
        RANDOM = WeakReference(random)
    }
    var requestCode: Int
    do {
        requestCode = random.nextInt(bound)
    } while (CALLBACKS.containsKey(requestCode))
    return requestCode
}

@PublishedApi
internal fun <T> appendActivityCallback(callback: T.(Int, Intent?) -> Unit): Int {
    // unsigned 16-bit int, as required by FragmentActivity precondition
    val requestCode = generateRequestCode(65535)
    CALLBACKS.append(requestCode, (callback as Any.(Int, Intent?) -> Unit))
    return requestCode
}

@PublishedApi
internal fun <T> appendPermissionCallback(callback: T.(Boolean) -> Unit): Int {
    // unsigned 8-bit int
    val requestCode = generateRequestCode(255)
    CALLBACKS.append(requestCode, (callback as Any.(Boolean) -> Unit))
    return requestCode
}

@PublishedApi
internal inline fun getCallback(requestCode: Int): Any? {
    val callback = CALLBACKS.get(requestCode) ?: return null
    CALLBACKS.remove(requestCode)
    return callback
}

inline fun <T : Activity> T.onActivityResult2(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
) = (getCallback(requestCode) as T.(Int, Intent?) -> Unit).invoke(this, resultCode, data)

inline fun <T : Fragment> T.onActivityResult2(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
) = (getCallback(requestCode) as T.(Int, Intent?) -> Unit).invoke(this, resultCode, data)

inline fun <T : android.support.v4.app.Fragment> T.onActivityResult2(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
) = (getCallback(requestCode) as T.(Int, Intent?) -> Unit).invoke(this, resultCode, data)

inline fun <T : Activity> T.onRequestPermissionsResult2(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
) = (getCallback(requestCode) as T.(Boolean) -> Unit).invoke(this, grantResults
        .all { it == PackageManager.PERMISSION_GRANTED })

inline fun <T : Fragment> T.onRequestPermissionsResult2(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
) = (getCallback(requestCode) as T.(Boolean) -> Unit).invoke(this, grantResults
        .all { it == PackageManager.PERMISSION_GRANTED })

inline fun <T : android.support.v4.app.Fragment> T.onRequestPermissionsResult2(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
) = (getCallback(requestCode) as T.(Boolean) -> Unit).invoke(this, grantResults
        .all { it == PackageManager.PERMISSION_GRANTED })