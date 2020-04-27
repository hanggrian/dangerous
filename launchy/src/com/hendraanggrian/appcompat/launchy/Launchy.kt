@file:Suppress("DEPRECATION")

package com.hendraanggrian.appcompat.launchy

import android.app.Activity
import android.app.Fragment
import android.content.Intent
import androidx.collection.SparseArrayCompat
import androidx.core.content.PermissionChecker
import java.lang.ref.WeakReference
import java.util.Random

object Launchy {
    /** Weak reference of Random to generate random number. */
    private var RANDOM: WeakReference<Random>? = null

    /**
     * Queued callbacks that will be invoked one-by-one on activity result.
     * Once invoked, callback will be removed from this collection.
     */
    private var ACTIVITY_CALLBACKS: SparseArrayCompat<Any.(Int, Intent?) -> Unit>? = null
    private var PERMISSION_CALLBACKS: SparseArrayCompat<Any.(Boolean) -> Unit>? = null

    /**
     * Redirect [Activity.onActivityResult],
     * so that it may be triggered on [launchActivity].
     */
    fun onActivityResult(
        activity: Activity,
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        ACTIVITY_CALLBACKS
            ?.slice(requestCode)
            ?.invoke(activity, resultCode, data)
    }

    /**
     * Redirect [Fragment.onActivityResult],
     * so that it may be triggered on [launchActivity].
     */
    fun onActivityResult(
        fragment: Fragment,
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        ACTIVITY_CALLBACKS
            ?.slice(requestCode)
            ?.invoke(fragment, resultCode, data)
    }

    /**
     * Redirect [android.support.v4.app.Fragment.onActivityResult],
     * so that it may be triggered on [launchActivity].
     */
    fun onActivityResult(
        fragment: androidx.fragment.app.Fragment,
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        ACTIVITY_CALLBACKS
            ?.slice(requestCode)
            ?.invoke(fragment, resultCode, data)
    }

    /**
     * Redirect [Activity.onRequestPermissionsResult],
     * so that it may be triggered on [launchPermission].
     */
    fun onRequestPermissionsResult(
        activity: Activity,
        requestCode: Int,
        grantResults: IntArray
    ) {
        PERMISSION_CALLBACKS
            ?.slice(requestCode)
            ?.invoke(activity, grantResults.all { it == PermissionChecker.PERMISSION_GRANTED })
    }

    /**
     * Redirect [Fragment.onRequestPermissionsResult],
     * so that it may be triggered on [launchPermission].
     */
    fun onRequestPermissionsResult(
        fragment: Fragment,
        requestCode: Int,
        grantResults: IntArray
    ) {
        PERMISSION_CALLBACKS
            ?.slice(requestCode)
            ?.invoke(fragment, grantResults.all { it == PermissionChecker.PERMISSION_GRANTED })
    }

    /**
     * Redirect [android.support.v4.app.Fragment.onRequestPermissionsResult],
     * so that it may be triggered on [launchPermission].
     */
    fun onRequestPermissionsResult(
        fragment: androidx.fragment.app.Fragment,
        requestCode: Int,
        grantResults: IntArray
    ) {
        PERMISSION_CALLBACKS
            ?.slice(requestCode)
            ?.invoke(fragment, grantResults.all { it == PermissionChecker.PERMISSION_GRANTED })
    }

    @Suppress("UNCHECKED_CAST")
    internal fun <T> appendActivity(callback: T.(Int, Intent?) -> Unit): Int {
        if (ACTIVITY_CALLBACKS == null) {
            ACTIVITY_CALLBACKS = SparseArrayCompat()
        }
        // unsigned 16-bit int, as required by FragmentActivity precondition
        val requestCode = ACTIVITY_CALLBACKS!! newRequestCode 65535
        ACTIVITY_CALLBACKS!!.append(requestCode, (callback as Any.(Int, Intent?) -> Unit))
        return requestCode
    }

    @Suppress("UNCHECKED_CAST")
    internal fun <T> appendPermission(callback: T.(Boolean) -> Unit): Int {
        if (PERMISSION_CALLBACKS == null) {
            PERMISSION_CALLBACKS = SparseArrayCompat()
        }
        // unsigned 8-bit int
        val requestCode = PERMISSION_CALLBACKS!! newRequestCode 255
        PERMISSION_CALLBACKS!!.append(requestCode, (callback as Any.(Boolean) -> Unit))
        return requestCode
    }

    @Suppress("NOTHING_TO_INLINE")
    private inline fun <T> SparseArrayCompat<T>.slice(requestCode: Int): T? {
        val callback = get(requestCode) ?: return null
        remove(requestCode)
        return callback
    }

    /**
     * Attempt to get [Random] instance from [WeakReference].
     * When no instance is found, create a new one and save it.
     * Then generate a random number that is guaranteed to be non-duplicate of [ACTIVITY_CALLBACKS] key.
     */
    private infix fun SparseArrayCompat<*>.newRequestCode(bound: Int): Int {
        var random = RANDOM?.get()
        if (random == null) {
            random = Random()
            RANDOM = WeakReference(random)
        }
        var requestCode: Int
        do {
            requestCode = random.nextInt(bound)
        } while (containsKey(requestCode))
        return requestCode
    }
}
