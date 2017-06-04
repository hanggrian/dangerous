package com.hendraanggrian.rx.activity;

import android.support.annotation.NonNull;

import io.reactivex.ObservableEmitter;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
final class EmitterWrapper<T> {

    @NonNull final Class<T> cls;
    @NonNull final ObservableEmitter<T> emitter;

    EmitterWrapper(@NonNull Class<T> cls, @NonNull ObservableEmitter<T> emitter) {
        this.cls = cls;
        this.emitter = emitter;
    }
}