package com.hendraanggrian.app

import android.content.Intent
import io.reactivex.ObservableEmitter

interface ActivityResultEmitter : ObservableEmitter<Intent> {

    /**
     * Ensure that Observable will only emits if result code match.
     */
    val resultCode: Int
}