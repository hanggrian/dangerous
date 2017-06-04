package com.hendraanggrian.rx.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.SparseArray;

import java.lang.ref.WeakReference;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public final class RxActivity {

    private final static SparseArray<ObservableEmitter<Result>> PENDING_REQUESTS = new SparseArray<>();
    private static WeakReference<Random> RANDOM;

    private RxActivity() {
    }

    @NonNull
    public static Observable<Result> startForAny(@NonNull final Activity activity, @NonNull final Intent intent) {
        return Observable.create(new ObservableOnSubscribe<Result>() {
            @Override
            public void subscribe(@io.reactivex.annotations.NonNull ObservableEmitter<Result> e) throws Exception {
                int requestCode = generateRandom();
                PENDING_REQUESTS.append(requestCode, e);
                activity.startActivityForResult(intent, requestCode);
            }
        });
    }

    @NonNull
    public static Observable<Intent> startForResult(@NonNull final Activity activity, @NonNull final Intent intent) {
        return startForAny(activity, intent).map(new Function<Result, Intent>() {
            @Override
            public Intent apply(@io.reactivex.annotations.NonNull Result result) throws Exception {
                return result.data;
            }
        });
    }

    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (PENDING_REQUESTS.indexOfKey(requestCode) > -1) {
            ObservableEmitter<Result> e = PENDING_REQUESTS.get(requestCode);
            if (resultCode == Activity.RESULT_OK) {
                e.onNext(new Result(requestCode, resultCode, data));
            } else {
                e.onError(new ActivityCanceledException());
            }
            e.onComplete();
            PENDING_REQUESTS.remove(requestCode);
        }
    }

    private static int generateRandom() {
        if (RANDOM == null) {
            RANDOM = new WeakReference<>(new Random());
        }
        Random random = RANDOM.get();
        if (random != null) {
            return random.nextInt(255);
        }
        random = new Random();
        RANDOM = new WeakReference<>(random);
        return random.nextInt(255);
    }
}