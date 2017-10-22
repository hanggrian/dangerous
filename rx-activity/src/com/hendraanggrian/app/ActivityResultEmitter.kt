package com.hendraanggrian.app

import android.content.Intent
import io.reactivex.*
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Cancellable

internal interface ActivityResultEmitter {

    /** Ensure that emitter will only emits if result code match [resultCode]. */
    val resultCode: Int

    /** Shall be shadowed by RxJava's emitters. */
    fun isDisposed(): Boolean
}

@PublishedApi
internal interface CompletableActivityResultEmitter : CompletableEmitter, ActivityResultEmitter {
    companion object {
        fun create(resultCode: Int, start: (Int) -> Unit): Completable = Completable.create { e ->
            val requestCode = RxActivity.nextRequestCode
            RxActivity.append(requestCode, object : CompletableActivityResultEmitter {
                override val resultCode: Int get() = resultCode
                override fun isDisposed(): Boolean = e.isDisposed
                override fun tryOnError(t: Throwable): Boolean = e.tryOnError(t)
                override fun onComplete() = e.onComplete()
                override fun setCancellable(c: Cancellable?) = e.setCancellable(c)
                override fun setDisposable(d: Disposable?) = e.setDisposable(d)
                override fun onError(t: Throwable) = e.onError(t)
            })
            start(requestCode)
        }
    }
}

@PublishedApi
internal interface ObservableActivityResultEmitter : ObservableEmitter<Intent>, ActivityResultEmitter {
    companion object {
        fun create(resultCode: Int, start: (Int) -> Unit): Observable<Intent> = Observable.create<Intent> { e ->
            val requestCode = RxActivity.nextRequestCode
            RxActivity.append(requestCode, object : ObservableActivityResultEmitter {
                override val resultCode: Int get() = resultCode
                override fun onError(t: Throwable) = e.onError(t)
                override fun isDisposed(): Boolean = e.isDisposed
                override fun onComplete() = e.onComplete()
                override fun tryOnError(t: Throwable) = e.tryOnError(t)
                override fun setDisposable(d: Disposable?) = e.setDisposable(d)
                override fun setCancellable(c: Cancellable?) = e.setCancellable(c)
                override fun serialize() = e.serialize()
                override fun onNext(value: Intent) = e.onNext(value)
            })
            start(requestCode)
        }
    }
}

@PublishedApi
internal interface SingleActivityResultEmitter : SingleEmitter<Intent>, ActivityResultEmitter {
    companion object {
        fun create(resultCode: Int, start: (Int) -> Unit): Single<Intent> = Single.create<Intent> { e ->
            val requestCode = RxActivity.nextRequestCode
            RxActivity.append(requestCode, object : SingleActivityResultEmitter {
                override val resultCode: Int get() = resultCode
                override fun onError(t: Throwable) = e.onError(t)
                override fun isDisposed(): Boolean = e.isDisposed
                override fun tryOnError(t: Throwable): Boolean = e.tryOnError(t)
                override fun setDisposable(s: Disposable?) = e.setDisposable(s)
                override fun onSuccess(t: Intent) = e.onSuccess(t)
                override fun setCancellable(c: Cancellable?) = e.setCancellable(c)
            })
            start(requestCode)
        }
    }
}