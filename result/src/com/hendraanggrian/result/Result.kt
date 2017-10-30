package com.hendraanggrian.result

import android.content.Intent
import android.support.v4.util.SparseArrayCompat
import java.lang.ref.WeakReference
import java.util.*

@PublishedApi
internal object Result {

    /** Weak reference of Random to generate random number. */
    private var RANDOM: WeakReference<Random>? = null

    /**
     * Queued callbacks that will be invoked one-by-one on activity result.
     * Once invoked, callback will be removed from this collection.
     */
    private val CALLBACKS: SparseArrayCompat<Any> = SparseArrayCompat()

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

    @Suppress("UNCHECKED_CAST")
    fun <T> appendActivityCallback(callback: T.(Int, Intent?) -> Unit): Int {
        // unsigned 16-bit int, as required by FragmentActivity precondition
        val requestCode = generateRequestCode(65535)
        CALLBACKS.append(requestCode, (callback as Any.(Int, Intent?) -> Unit))
        return requestCode
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> appendPermissionCallback(callback: T.(Boolean) -> Unit): Int {
        // unsigned 8-bit int
        val requestCode = generateRequestCode(255)
        CALLBACKS.append(requestCode, (callback as Any.(Boolean) -> Unit))
        return requestCode
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getCallback(requestCode: Int): T? {
        val callback = CALLBACKS.get(requestCode) ?: return null
        CALLBACKS.remove(requestCode)
        return callback as T
    }
}