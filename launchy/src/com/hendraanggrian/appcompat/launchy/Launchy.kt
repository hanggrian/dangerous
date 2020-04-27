@file:Suppress("DEPRECATION")

package com.hendraanggrian.appcompat.launchy

import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.util.Log
import androidx.collection.SparseArrayCompat
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker

/**
 * Queued callbacks that will be invoked one-by-one on activity result.
 * Once invoked, callback will be removed from this collection.
 */
object Launchy {
    private var ACTIVITY_CALLBACKS: SparseArrayCompat<Any.(Int, Intent?) -> Unit>? = null
    private var PERMISSION_CALLBACKS: SparseArrayCompat<Any.(Boolean) -> Unit>? = null
    private var DEBUG: Boolean = false

    private const val TAG = "Launchy"

    fun setDebug(debug: Boolean = true) {
        DEBUG = debug
    }

    private fun StringBuilder.print() {
        if (DEBUG) Log.d(TAG, "$this")
    }

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
        val sb = StringBuilder("onActivityResult #$requestCode: ")
        when (val callback = ACTIVITY_CALLBACKS?.slice(requestCode)) {
            null -> sb.append("no result")
            else -> try {
                callback(activity, resultCode, data)
                sb.append("success")
            } catch (e: Exception) {
                if (DEBUG) e.printStackTrace()
                sb.append("failed")
            }
        }
        sb.print()
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
        val sb = StringBuilder("onActivityResult #$requestCode: ")
        when (val callback = ACTIVITY_CALLBACKS?.slice(requestCode)) {
            null -> sb.append("no result")
            else -> try {
                callback(fragment, resultCode, data)
                sb.append("success")
            } catch (e: Exception) {
                if (DEBUG) e.printStackTrace()
                sb.append("failed")
            }
        }
        sb.print()
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
        val sb = StringBuilder("onActivityResult #$requestCode: ")
        when (val callback = ACTIVITY_CALLBACKS?.slice(requestCode)) {
            null -> sb.append("no result")
            else -> try {
                callback(fragment, resultCode, data)
                sb.append("success")
            } catch (e: Exception) {
                if (DEBUG) e.printStackTrace()
                sb.append("failed")
            }
        }
        sb.print()
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
        val sb = StringBuilder("onRequestPermissionsResult #$requestCode: ")
        when (val callback = PERMISSION_CALLBACKS?.slice(requestCode)) {
            null -> sb.append("no result")
            else -> try {
                callback(activity, grantResults.all { it == PermissionChecker.PERMISSION_GRANTED })
                sb.append("success")
            } catch (e: Exception) {
                if (DEBUG) e.printStackTrace()
                sb.append("failed")
            }
        }
        sb.print()
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
        val sb = StringBuilder("onRequestPermissionsResult #$requestCode: ")
        when (val callback = PERMISSION_CALLBACKS?.slice(requestCode)) {
            null -> sb.append("no result")
            else -> try {
                callback(fragment, grantResults.all { it == PermissionChecker.PERMISSION_GRANTED })
                sb.append("success")
            } catch (e: Exception) {
                if (DEBUG) e.printStackTrace()
                sb.append("failed")
            }
        }
        sb.print()
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
        val sb = StringBuilder("onRequestPermissionsResult #$requestCode: ")
        when (val callback = PERMISSION_CALLBACKS?.slice(requestCode)) {
            null -> sb.append("no result")
            else -> try {
                callback(fragment, grantResults.all { it == PermissionChecker.PERMISSION_GRANTED })
                sb.append("success")
            } catch (e: Exception) {
                if (DEBUG) e.printStackTrace()
                sb.append("failed")
            }
        }
        sb.print()
    }

    @Suppress("UNCHECKED_CAST")
    internal fun <T> appendActivity(callback: T.(Int, Intent?) -> Unit): Int {
        val sb = StringBuilder("appendActivity #")
        if (ACTIVITY_CALLBACKS == null) {
            ACTIVITY_CALLBACKS = SparseArrayCompat()
        }
        // unsigned 16-bit int, as required by FragmentActivity precondition
        val requestCode = requestCodeOf(ACTIVITY_CALLBACKS!!, 65535)
        ACTIVITY_CALLBACKS!!.append(requestCode, (callback as Any.(Int, Intent?) -> Unit))
        sb.append(requestCode).print()
        return requestCode
    }

    @Suppress("UNCHECKED_CAST")
    internal fun <T> appendPermission(callback: T.(Boolean) -> Unit): Int {
        val sb = StringBuilder("appendPermission #")
        if (PERMISSION_CALLBACKS == null) {
            PERMISSION_CALLBACKS = SparseArrayCompat()
        }
        // unsigned 8-bit int
        val requestCode = requestCodeOf(PERMISSION_CALLBACKS!!, 255)
        PERMISSION_CALLBACKS!!.append(requestCode, (callback as Any.(Boolean) -> Unit))
        sb.append(requestCode).print()
        return requestCode
    }

    @Suppress("NOTHING_TO_INLINE")
    private inline fun <T> SparseArrayCompat<T>.slice(requestCode: Int): T? {
        val callback = get(requestCode) ?: return null
        remove(requestCode)
        return callback
    }

    /** Generate a random number that is guaranteed to be non-duplicate. */
    @Suppress("NOTHING_TO_INLINE")
    private inline fun requestCodeOf(array: SparseArrayCompat<*>, bound: Int): Int {
        var requestCode: Int
        do {
            requestCode = (0..bound).random()
        } while (array.containsKey(requestCode))
        return requestCode
    }
}
