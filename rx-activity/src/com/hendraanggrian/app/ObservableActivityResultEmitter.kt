package com.hendraanggrian.app

import android.content.Intent
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Cancellable

@PublishedApi
internal interface ObservableActivityResultEmitter : ObservableEmitter<Intent>, ActivityResultEmitter {
    companion object {
        fun create(resultCode: Int, start: (Int) -> Unit): Observable<Intent> = Observable.create<Intent> { e ->
            val requestCode = RxActivity.nextRequestCode
            RxActivity.append(requestCode, object : ObservableActivityResultEmitter {
                override val resultCode: Int get() = resultCode
                override fun onNext(value: Intent) = e.onNext(value)
                override fun onError(error: Throwable) = e.onError(error)
                override fun setCancellable(c: Cancellable?) = e.setCancellable(c)
                override fun onComplete() = e.onComplete()
                override fun isDisposed() = e.isDisposed
                override fun setDisposable(d: Disposable?) = e.setDisposable(d)
                override fun serialize() = e.serialize()
            })
            start(requestCode)
        }
    }
}