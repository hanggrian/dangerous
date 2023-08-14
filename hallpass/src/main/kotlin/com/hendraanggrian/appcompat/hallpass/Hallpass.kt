package com.hendraanggrian.appcompat.hallpass

import android.util.Log
import androidx.collection.SparseArrayCompat
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker

typealias PermissionCallback = ActivityCompat.OnRequestPermissionsResultCallback

object Hallpass : PermissionCallback {
    private var LISTENERS: SparseArrayCompat<(Boolean) -> Unit>? = null
    private var DEBUG: Boolean = false

    private const val TAG = "Hallpass"

    fun setDebug(debug: Boolean = true) {
        DEBUG = debug
    }

    internal fun debug(message: Any) {
        if (DEBUG) {
            Log.d(TAG, "$message")
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        val sb = StringBuilder("onRequestPermissionsResult #$requestCode: ")
        when (val callback = LISTENERS?.slice(requestCode)) {
            null -> sb.append("no result")
            else -> try {
                callback(grantResults.all { it == PermissionChecker.PERMISSION_GRANTED })
                sb.append("success")
            } catch (e: Exception) {
                if (DEBUG) {
                    e.printStackTrace()
                }
                sb.append("failed")
            }
        }
        debug(sb)
    }

    @Suppress("UNCHECKED_CAST")
    internal fun appendPermission(callback: (Boolean) -> Unit): Int {
        val sb = StringBuilder("appendPermission #")
        if (LISTENERS == null) {
            LISTENERS = SparseArrayCompat()
        }
        // unsigned 8-bit int
        val requestCode = requestCodeOf(LISTENERS!!, 255)
        LISTENERS!!.append(requestCode, callback)
        debug(sb.append(requestCode))
        return requestCode
    }

    private fun <T> SparseArrayCompat<T>.slice(requestCode: Int): T? {
        val callback = get(requestCode) ?: return null
        remove(requestCode)
        return callback
    }

    /** Generate a random number that is guaranteed to be non-duplicate. */
    private fun requestCodeOf(array: SparseArrayCompat<*>, bound: Int): Int {
        var requestCode: Int
        do {
            requestCode = (0..bound).random()
        } while (array.containsKey(requestCode))
        return requestCode
    }
}
