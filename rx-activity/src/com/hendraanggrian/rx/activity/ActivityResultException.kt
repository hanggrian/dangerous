@file:Suppress("UNUSED")

package com.hendraanggrian.rx.activity

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
open class ActivityResultException : Exception {

    val resultCode: Int

    constructor(resultCode: Int) {
        this.resultCode = resultCode
    }

    constructor(resultCode: Int, name: String) : super(name) {
        this.resultCode = resultCode
    }
}