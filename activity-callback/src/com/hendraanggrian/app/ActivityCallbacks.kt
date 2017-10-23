@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")

package com.hendraanggrian.app

import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.support.v4.util.SparseArrayCompat
import java.lang.ref.WeakReference
import java.util.*

/** Weak reference of Random to generate random number. */
private var RANDOM: WeakReference<Random?> = WeakReference(null)

/** Collection of reactive emitters that will emits one-by-one on activity result. Once emitted, emitter is cease to exist from this collection. */
@PublishedApi
internal val CALLBACKS: SparseArrayCompat<Any.(requestCode: Int, resultCode: Int, data: Intent?) -> Unit> = SparseArrayCompat()

/**
 * Attempt to get Random instance from WeakReference.
 * when no instance is found, create a new one and save it.
 * At this point instance can be ensured not null.
 */
@PublishedApi
internal val nextRequestCode: Int
    get() {
        var random = RANDOM.get()
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
internal fun <T> queue(requestCode: Int, block: T.(requestCode: Int, resultCode: Int, data: Intent?) -> Unit) =
        CALLBACKS.append(requestCode, (block as Any.(requestCode: Int, resultCode: Int, data: Intent?) -> Unit))

@PublishedApi
internal inline fun <T> T.onActivityResultInternal(requestCode: Int, resultCode: Int, data: Intent?) {
    val callback = CALLBACKS.get(requestCode) ?: return
    CALLBACKS.remove(requestCode)
    (callback as T.(requestCode: Int, resultCode: Int, data: Intent?) -> Unit).invoke(this, requestCode, resultCode, data)
}

inline fun <T : Activity> T.onActivityResult2(requestCode: Int, resultCode: Int, data: Intent?) = onActivityResultInternal(requestCode, resultCode, data)

inline fun <T : Fragment> T.onActivityResult2(requestCode: Int, resultCode: Int, data: Intent?) = onActivityResultInternal(requestCode, resultCode, data)

inline fun <T : android.support.v4.app.Fragment> T.onActivityResult2(requestCode: Int, resultCode: Int, data: Intent?) = onActivityResultInternal(requestCode, resultCode, data)