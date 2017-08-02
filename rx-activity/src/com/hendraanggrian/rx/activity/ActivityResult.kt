@file:JvmName("ActivityResult")

package com.hendraanggrian.rx.activity

import android.app.Activity
import android.content.Intent

/**
 * Data class representing result of an activity started with RxActivity's startActivityForResultBy(...).

 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
data class ActivityResult internal constructor(val requestCode: Int, val resultCode: Int, val data: Intent?) {

    override fun toString() = "ActivityResult[requestCode=$requestCode, resultCode=${when (resultCode) {
        Activity.RESULT_OK -> "RESULT_OK"
        Activity.RESULT_CANCELED -> "RESULT_CANCELED"
        else -> resultCode.toString()
    }}]"
}