package com.hendraanggrian.app

import android.content.Intent
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Cancellable

@PublishedApi
internal interface SingleActivityResultEmitter : SingleEmitter<Intent>, ActivityResultEmitter {
    companion object {
        fun create(resultCode: Int, start: (Int) -> Unit): Single<Intent> = Single.create<Intent> { e ->
            val requestCode = RxActivity.nextRequestCode
            RxActivity.append(requestCode, object : SingleActivityResultEmitter {
                override val resultCode: Int get() = resultCode
                override fun onError(t: Throwable) = e.onError(t)
                override fun isDisposed(): Boolean = e.isDisposed
                override fun setDisposable(s: Disposable?) = e.setDisposable(s)
                override fun onSuccess(t: Intent) = e.onSuccess(t)
                override fun setCancellable(c: Cancellable?) = e.setCancellable(c)
            })
            start(requestCode)
        }
    }
}