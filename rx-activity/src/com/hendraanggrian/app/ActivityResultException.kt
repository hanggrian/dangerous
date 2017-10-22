package com.hendraanggrian.app

import android.content.Intent

open class ActivityResultException : Exception {

    private var mRequestCode: Int? = null
    private var mResultCode: Int? = null
    private var mData: Intent? = null

    constructor() : super()

    constructor(message: String) : super(message)

    constructor(message: String, cause: Throwable) : super(message, cause)

    internal constructor(requestCode: Int, resultCode: Int, data: Intent?) : this("Activity with request code $requestCode fails expected result code check.") {
        mRequestCode = requestCode
        mResultCode = resultCode
        mData = data
    }

    val requestCode: Int get() = mRequestCode!!

    val resultCode: Int get() = mResultCode!!

    val data: Intent? get() = mData
}