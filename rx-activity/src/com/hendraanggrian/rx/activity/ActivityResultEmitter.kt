package com.hendraanggrian.rx.activity

import io.reactivex.ObservableEmitter
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Cancellable

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
internal interface ActivityResultEmitter<T> : ObservableEmitter<T> {

    val type: Int

    companion object {
        val TYPE_OK = 0
        val TYPE_RESULT = 1

        fun <T> getInstance(type: Int, e: ObservableEmitter<T>) = object : ActivityResultEmitter<T> {
            override val type = type

            override fun setDisposable(d: Disposable?) {
                e.setDisposable(d)
            }

            override fun setCancellable(c: Cancellable?) {
                e.setCancellable(c)
            }

            override fun isDisposed() = e.isDisposed

            override fun serialize() = e.serialize()

            override fun onNext(@io.reactivex.annotations.NonNull value: T) {
                e.onNext(value)
            }

            override fun onError(@io.reactivex.annotations.NonNull error: Throwable) {
                e.onError(error)
            }

            override fun onComplete() {
                e.onComplete()
            }
        }
    }
}