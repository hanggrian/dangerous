@file:Suppress("UNUSED")

package com.hendraanggrian.app

open class ActivityResultException : Exception {

    val requestCode: Int

    constructor(resultCode: Int) {
        this.requestCode = resultCode
    }

    constructor(resultCode: Int, name: String) : super(name) {
        this.requestCode = resultCode
    }
}