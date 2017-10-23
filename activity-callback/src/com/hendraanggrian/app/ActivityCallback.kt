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
private val STARTERS: SparseArrayCompat<ActivityStarter<out Any>> = SparseArrayCompat()

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
internal fun append(requestCode: Int, starter: ActivityStarter<out Any>) = STARTERS.append(requestCode, starter)

fun Activity.onActivityResult2(requestCode: Int, resultCode: Int, data: Intent?) {
    val starter = STARTERS.get(requestCode) ?: return
    STARTERS.remove(requestCode)
    starter as ActivityStarter<Activity>
    if (starter.resultCode != resultCode) starter.fallback(this, requestCode, resultCode, data)
    else starter.block(this, requestCode, resultCode, data)
}

fun Fragment.onActivityResult2(requestCode: Int, resultCode: Int, data: Intent?) {
    val starter = STARTERS.get(requestCode) ?: return
    STARTERS.remove(requestCode)
    starter as ActivityStarter<Fragment>
    if (starter.resultCode != resultCode) starter.fallback(this, requestCode, resultCode, data)
    else starter.block(this, requestCode, resultCode, data)
}