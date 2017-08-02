package com.hendraanggrian.rx.activity.internal

import io.reactivex.ObservableEmitter
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Cancellable

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
internal interface ActivityResultEmitter<T> : ObservableEmitter<T> {

    val type: Int

    companion object {
        internal const val TYPE_OK = 0
        internal const val TYPE_RESULT = 1

        fun <T> getInstance(type: Int, e: ObservableEmitter<T>) = object : ActivityResultEmitter<T> {
            override val type = type
            override fun setDisposable(d: Disposable?) = e.setDisposable(d)
            override fun setCancellable(c: Cancellable?) = e.setCancellable(c)
            override fun isDisposed() = e.isDisposed
            override fun serialize() = e.serialize()
            override fun onNext(@NonNull value: T) = e.onNext(value)
            override fun onError(@NonNull error: Throwable) = e.onError(error)
            override fun onComplete() = e.onComplete()
        }
    }
}