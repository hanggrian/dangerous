package com.hendraanggrian.rx.activity.internal

import android.content.Intent
import io.reactivex.ObservableEmitter

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
interface ActivityResultEmitter : ObservableEmitter<Intent> {

    /**
     * Ensure that Observable will only emits if result code match.
     */
    val resultCode: Int
}