package com.hendraanggrian.rx.activity.internal

import android.content.Intent
import io.reactivex.ObservableEmitter
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Cancellable

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class ActivityResultEmitterImpl(
        override val resultCode: Int,
        val e: ObservableEmitter<Intent>
) : ActivityResultEmitter {

    override fun onNext(value: Intent) = e.onNext(value)
    override fun onError(error: Throwable) = e.onError(error)
    override fun setCancellable(c: Cancellable?) = e.setCancellable(c)
    override fun onComplete() = e.onComplete()
    override fun isDisposed() = e.isDisposed
    override fun setDisposable(d: Disposable?) = e.setDisposable(d)
    override fun serialize() = e.serialize()
}