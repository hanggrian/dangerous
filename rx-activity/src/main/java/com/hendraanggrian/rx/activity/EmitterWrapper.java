package com.hendraanggrian.rx.activity;

import android.support.annotation.NonNull;

import io.reactivex.ObservableEmitter;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
final class EmitterWrapper<T> {

    final boolean isSimple;
    @NonNull final ObservableEmitter<T> emitter;

    EmitterWrapper(boolean isSimple, @NonNull ObservableEmitter<T> emitter) {
        this.isSimple = isSimple;
        this.emitter = emitter;
    }
}