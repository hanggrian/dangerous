package com.hendraanggrian.rx.activity

import android.app.Activity
import android.content.Intent

/**
 * Data class representing result of an activity started with RxActivity's startForResult(...).

 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
data class ActivityResult internal constructor(val requestCode: Int, val resultCode: Int, val data: Intent?) {

    override fun toString() = String.format("ActivityResult[requestCode=%s, resultCode=%s]", requestCode, when (resultCode) {
        Activity.RESULT_OK -> "RESULT_OK"
        Activity.RESULT_CANCELED -> "RESULT_CANCELED"
        else -> resultCode
    })
}