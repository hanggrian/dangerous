@file:Suppress("UNUSED")

package com.hendraanggrian.app

open class ActivityResultException : Exception {

    val requestCode: Int

    @JvmOverloads
    constructor(requestCode: Int = 0) {
        this.requestCode = requestCode
    }

    @JvmOverloads
    constructor(requestCode: Int = 0, message: String) : super(message) {
        this.requestCode = requestCode
    }
}