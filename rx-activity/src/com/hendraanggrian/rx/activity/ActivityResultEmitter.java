package com.hendraanggrian.rx.activity;

import android.support.annotation.NonNull;

import io.reactivex.ObservableEmitter;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
abstract class ActivityResultEmitter<T> implements ObservableEmitter<T> {

    static final int TYPE_OK = 0;
    static final int TYPE_RESULT = 1;

    abstract int getType();

    @NonNull
    static <T> ActivityResultEmitter<T> create(final int type, @NonNull final ObservableEmitter<T> e) {
        return new ActivityResultEmitter<T>() {
            @Override
            int getType() {
                return type;
            }

            @Override
            public void setDisposable(@Nullable Disposable d) {
                e.setDisposable(d);
            }

            @Override
            public void setCancellable(@Nullable Cancellable c) {
                e.setCancellable(c);
            }

            @Override
            public boolean isDisposed() {
                return e.isDisposed();
            }

            @Override
            public ObservableEmitter<T> serialize() {
                return e.serialize();
            }

            @Override
            public void onNext(@io.reactivex.annotations.NonNull T value) {
                e.onNext(value);
            }

            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable error) {
                e.onError(error);
            }

            @Override
            public void onComplete() {
                e.onComplete();
            }
        };
    }
}