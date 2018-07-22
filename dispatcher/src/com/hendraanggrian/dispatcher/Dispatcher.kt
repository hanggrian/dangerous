@file:Suppress("DEPRECATION")

package com.hendraanggrian.dispatcher

import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.collection.SparseArrayCompat
import java.lang.ref.WeakReference
import java.util.Random

object Dispatcher {

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

    @PublishedApi
    @Suppress("UNCHECKED_CAST")
    internal fun <T> appendActivity(callback: T.(Int, Intent?) -> Unit): Int {
        // unsigned 16-bit int, as required by FragmentActivity precondition
        val requestCode = generateRequestCode(65535)
        CALLBACKS.append(requestCode, (callback as Any.(Int, Intent?) -> Unit))
        return requestCode
    }

    @PublishedApi
    @Suppress("UNCHECKED_CAST")
    internal fun <T> appendPermission(callback: T.(Boolean) -> Unit): Int {
        // unsigned 8-bit int
        val requestCode = generateRequestCode(255)
        CALLBACKS.append(requestCode, (callback as Any.(Boolean) -> Unit))
        return requestCode
    }

    @PublishedApi
    @Suppress("UNCHECKED_CAST")
    internal fun <T> getCallback(requestCode: Int): T? {
        val callback = CALLBACKS.get(requestCode) ?: return null
        CALLBACKS.remove(requestCode)
        return callback as T
    }

    /**
     * Redirect [Activity.onActivityResult],
     * so that it may be triggered on [startActivity].
     */
    @Suppress("NOTHING_TO_INLINE")
    inline fun <T : Activity> onActivityResult(
        activity: T,
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) = getCallback<T.(Int, Intent?) -> Unit>(requestCode)
        ?.invoke(activity, resultCode, data)

    /**
     * Redirect [Fragment.onActivityResult],
     * so that it may be triggered on [startActivity].
     */
    @Suppress("NOTHING_TO_INLINE")
    inline fun <T : Fragment> onActivityResult(
        fragment: T,
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) = getCallback<T.(Int, Intent?) -> Unit>(requestCode)
        ?.invoke(fragment, resultCode, data)

    /**
     * Redirect [android.support.v4.app.Fragment.onActivityResult],
     * so that it may be triggered on [startActivity].
     */
    @Suppress("NOTHING_TO_INLINE")
    inline fun <T : androidx.fragment.app.Fragment> onActivityResult(
        fragment: T,
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) = getCallback<T.(Int, Intent?) -> Unit>(requestCode)
        ?.invoke(fragment, resultCode, data)

    /**
     * Redirect [Activity.onRequestPermissionsResult],
     * so that it may be triggered on [requestPermissions].
     */
    @Suppress("NOTHING_TO_INLINE")
    inline fun <T : Activity> onRequestPermissionsResult(
        activity: T,
        requestCode: Int,
        grantResults: IntArray
    ) = Dispatcher.getCallback<T.(Boolean) -> Unit>(requestCode)
        ?.invoke(activity, grantResults.all { it == PERMISSION_GRANTED })

    /**
     * Redirect [Fragment.onRequestPermissionsResult],
     * so that it may be triggered on [requestPermissions].
     */
    @Suppress("NOTHING_TO_INLINE")
    inline fun <T : Fragment> onRequestPermissionsResult(
        fragment: T,
        requestCode: Int,
        grantResults: IntArray
    ) = Dispatcher.getCallback<T.(Boolean) -> Unit>(requestCode)
        ?.invoke(fragment, grantResults.all { it == PERMISSION_GRANTED })

    /**
     * Redirect [android.support.v4.app.Fragment.onRequestPermissionsResult],
     * so that it may be triggered on [requestPermissions].
     */
    @Suppress("NOTHING_TO_INLINE")
    inline fun <T : androidx.fragment.app.Fragment> onRequestPermissionsResult(
        fragment: T,
        requestCode: Int,
        grantResults: IntArray
    ) = Dispatcher.getCallback<T.(Boolean) -> Unit>(requestCode)
        ?.invoke(fragment, grantResults.all { it == PERMISSION_GRANTED })
}