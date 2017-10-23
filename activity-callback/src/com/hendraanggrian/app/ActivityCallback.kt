package com.hendraanggrian.app

import android.app.Activity
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.util.SparseArrayCompat
import java.lang.ref.WeakReference
import java.util.*

/** Weak reference of Random to generate random number. */
private var RANDOM: WeakReference<Random?> = WeakReference(null)

/** Collection of reactive emitters that will emits one-by-one on activity result. Once emitted, emitter is cease to exist from this collection. */
private val STARTERS: SparseArrayCompat<Any.(requestCode: Int, resultCode: Int, data: Intent?) -> Unit> = SparseArrayCompat()

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
        } while (STARTERS.indexOfKey(requestCode) > -1)
        return requestCode
    }

@PublishedApi
internal fun <T> append(requestCode: Int, block: T.(requestCode: Int, resultCode: Int, data: Intent?) -> Unit) =
        STARTERS.append(requestCode, (block as Any.(requestCode: Int, resultCode: Int, data: Intent?) -> Unit))

fun <T : Activity> T.onActivityResult2(requestCode: Int, resultCode: Int, data: Intent?) {
    val block = STARTERS.get(requestCode) ?: return
    STARTERS.remove(requestCode)
    block(this, requestCode, resultCode, data)
}

fun <T : Fragment> T.onActivityResult2(requestCode: Int, resultCode: Int, data: Intent?) {
    val block = STARTERS.get(requestCode) ?: return
    STARTERS.remove(requestCode)
    block(this, requestCode, resultCode, data)
}