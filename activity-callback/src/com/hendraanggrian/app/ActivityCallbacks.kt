@file:JvmMultifileClass
@file:JvmName("ActivityCallbacks")
@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")

package com.hendraanggrian.app

import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.support.v4.util.SparseArrayCompat
import java.lang.ref.WeakReference
import java.util.*

/** Weak reference of Random to generate random number. */
private var RANDOM: WeakReference<Random?>? = null

/**
 * Queued callbacks that will be invoked one-by-one on activity result.
 * Once invoked, callback will be removed from this collection.
 */
@PublishedApi internal val CALLBACKS: SparseArrayCompat<Any.(Int, Intent?) -> Unit> =
        SparseArrayCompat()

/**
 * Attempt to get [Random] instance from [WeakReference].
 * When no instance is found, create a new one and save it.
 * Then generate a random number that is guaranteed to be non-duplicate of [CALLBACKS] key.
 */
@PublishedApi
internal fun generateRequestCode(): Int {
    var random = RANDOM?.get()
    if (random == null) {
        random = Random()
        RANDOM = WeakReference(random)
    }
    var requestCode: Int
    do {
        // 16-bit int, as required by FragmentActivity precondition
        requestCode = random.nextInt(65535)
    } while (CALLBACKS.indexOfKey(requestCode) > -1)
    return requestCode
}

@PublishedApi
internal fun <T> append(requestCode: Int, callback: T.(Int, Intent?) -> Unit) =
        CALLBACKS.append(requestCode, (callback as Any.(Int, Intent?) -> Unit))

@PublishedApi
internal inline fun <T> T.onActivityResultInternal(requestCode: Int, resultCode: Int, data: Intent?) {
    val callback = CALLBACKS.get(requestCode) ?: return
    CALLBACKS.remove(requestCode)
    (callback as T.(Int, Intent?) -> Unit).invoke(this, resultCode, data)
}

inline fun <T : Activity> T.notifyOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?) =
        onActivityResultInternal(requestCode, resultCode, data)

inline fun <T : Fragment> T.notifyOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?) =
        onActivityResultInternal(requestCode, resultCode, data)

inline fun <T : android.support.v4.app.Fragment> T.notifyOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?) =
        onActivityResultInternal(requestCode, resultCode, data)