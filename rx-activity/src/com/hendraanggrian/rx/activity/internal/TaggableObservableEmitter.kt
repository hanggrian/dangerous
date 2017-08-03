package com.hendraanggrian.rx.activity.internal

import io.reactivex.ObservableEmitter
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Cancellable

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
internal interface TaggableObservableEmitter<T> : ObservableEmitter<T> {

    var tag: Any?
}

internal fun <T> ObservableEmitter<T>.toTaggedEmitter() = object : TaggableObservableEmitter<T> {
    override var tag: Any? = null
    override fun setDisposable(d: Disposable?) = this@toTaggedEmitter.setDisposable(d)
    override fun setCancellable(c: Cancellable?) = this@toTaggedEmitter.setCancellable(c)
    override fun isDisposed() = this@toTaggedEmitter.isDisposed
    override fun serialize() = this@toTaggedEmitter.serialize()
    override fun onNext(@NonNull value: T) = this@toTaggedEmitter.onNext(value)
    override fun onError(@NonNull error: Throwable) = this@toTaggedEmitter.onError(error)
    override fun onComplete() = this@toTaggedEmitter.onComplete()
}