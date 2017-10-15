package com.hendraanggrian.app

/**
 * Ensure that Observable will only emits if result code match [resultCode].
 */
internal interface ActivityResultEmitter {

    val resultCode: Int
}