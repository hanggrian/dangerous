package com.hendraanggrian.rx.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;

import java.lang.ref.WeakReference;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public final class RxActivity {

    /**
     * Max range of request code to pass {@link android.support.v4.app.FragmentActivity#checkForValidRequestCode} precondition.
     */
    private static final int MAX_REQUEST_CODE = 65535; // 16-bit int

    /**
     * Weak reference of Random to generate random number
     * below {@link RxActivity#MAX_REQUEST_CODE}
     * and not already queued in {@link RxActivity#QUEUES}.
     */
    @Nullable static WeakReference<Random> RANDOM_REQUEST_CODE;

    /**
     * Collection of reactive emitters that will emits one-by-one on activity result.
     * Once emitted, emitter will no longer exists in this collection.
     */
    @NonNull final static SparseArray<ActivityResultEmitter<?>> QUEUES = new SparseArray<>();

    private RxActivity() {
    }

    @NonNull
    public static Observable<Intent> startForOk(@NonNull final Activity activity, @NonNull Intent intent) {
        return createStarter(
                ActivityResultEmitter.TYPE_OK,
                ActivityStartable.fromActivity(activity),
                intent,
                null);
    }

    @NonNull
    public static Observable<Intent> startForOk(@NonNull final Activity activity, @NonNull Intent intent, @Nullable Bundle options) {
        return createStarter(
                ActivityResultEmitter.TYPE_OK,
                ActivityStartable.fromActivity(activity),
                intent,
                options);
    }

    @NonNull
    public static Observable<ActivityResult> startForResult(@NonNull final Activity activity, @NonNull Intent intent) {
        return createStarter(
                ActivityResultEmitter.TYPE_RESULT,
                ActivityStartable.fromActivity(activity),
                intent,
                null);
    }

    @NonNull
    public static Observable<ActivityResult> startForResult(@NonNull final Activity activity, @NonNull Intent intent, @Nullable Bundle options) {
        return createStarter(
                ActivityResultEmitter.TYPE_RESULT,
                ActivityStartable.fromActivity(activity),
                intent,
                options);
    }

    @NonNull
    public static Observable<Intent> startForOk(@NonNull final Fragment fragment, @NonNull Intent intent) {
        return createStarter(
                ActivityResultEmitter.TYPE_OK,
                ActivityStartable.fromFragment(fragment),
                intent,
                null);
    }

    @NonNull
    public static Observable<Intent> startForOk(@NonNull final Fragment fragment, @NonNull Intent intent, @Nullable Bundle options) {
        return createStarter(
                ActivityResultEmitter.TYPE_OK,
                ActivityStartable.fromFragment(fragment),
                intent,
                options);
    }

    @NonNull
    public static Observable<ActivityResult> startForResult(@NonNull final Fragment fragment, @NonNull Intent intent) {
        return createStarter(
                ActivityResultEmitter.TYPE_RESULT,
                ActivityStartable.fromFragment(fragment),
                intent,
                null);
    }

    @NonNull
    public static Observable<ActivityResult> startForResult(@NonNull final Fragment fragment, @NonNull Intent intent, @Nullable Bundle options) {
        return createStarter(
                ActivityResultEmitter.TYPE_RESULT,
                ActivityStartable.fromFragment(fragment),
                intent,
                options);
    }

    @NonNull
    public static Observable<Intent> startForOk(@NonNull final android.support.v4.app.Fragment fragment, @NonNull Intent intent) {
        return createStarter(
                ActivityResultEmitter.TYPE_OK,
                ActivityStartable.fromSupportFragment(fragment),
                intent,
                null);
    }

    @NonNull
    public static Observable<Intent> startForOk(@NonNull final android.support.v4.app.Fragment fragment, @NonNull Intent intent, @Nullable Bundle options) {
        return createStarter(
                ActivityResultEmitter.TYPE_OK,
                ActivityStartable.fromSupportFragment(fragment),
                intent,
                options);
    }

    @NonNull
    public static Observable<ActivityResult> startForResult(@NonNull final android.support.v4.app.Fragment fragment, @NonNull Intent intent) {
        return createStarter(
                ActivityResultEmitter.TYPE_RESULT,
                ActivityStartable.fromSupportFragment(fragment),
                intent,
                null);
    }

    @NonNull
    public static Observable<ActivityResult> startForResult(@NonNull final android.support.v4.app.Fragment fragment, @NonNull Intent intent, @Nullable Bundle options) {
        return createStarter(
                ActivityResultEmitter.TYPE_RESULT,
                ActivityStartable.fromSupportFragment(fragment),
                intent,
                options);
    }

    @NonNull
    private static <T> Observable<T> createStarter(final int type, @NonNull final ActivityStartable startable, @NonNull final Intent intent, @Nullable final Bundle options) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(@io.reactivex.annotations.NonNull ObservableEmitter<T> e) throws Exception {
                if (intent.resolveActivity(startable.getPackageManager()) == null) {
                    e.onError(new ActivityNotFoundException("No activity for this intent found."));
                } else {
                    int requestCode = generateRequestCode();
                    QUEUES.append(requestCode, ActivityResultEmitter.create(type, e));
                    if (Build.VERSION.SDK_INT >= 16) {
                        startable.startActivityForResult(intent, requestCode, options);
                    } else {
                        startable.startActivityForResult(intent, requestCode);
                    }
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (QUEUES.indexOfKey(requestCode) > -1) {
            ActivityResultEmitter e = QUEUES.get(requestCode);
            if (!e.isDisposed()) {
                ActivityResult result = new ActivityResult(requestCode, resultCode, data);
                if (e.getType() == ActivityResultEmitter.TYPE_RESULT) {
                    e.onNext(result);
                } else {
                    if (resultCode == Activity.RESULT_OK) {
                        e.onNext(result.getData());
                    } else {
                        e.onError(new ActivityCanceledException(result.toString()));
                    }
                }
                e.onComplete();
            }
            QUEUES.remove(requestCode);
        }
    }

    static int generateRequestCode() {
        if (RANDOM_REQUEST_CODE == null) {
            RANDOM_REQUEST_CODE = new WeakReference<>(new Random());
        }
        Random random = RANDOM_REQUEST_CODE.get();
        if (random == null) {
            random = new Random();
            RANDOM_REQUEST_CODE = new WeakReference<>(random);
        }
        int requestCode = random.nextInt(MAX_REQUEST_CODE);
        if (QUEUES.indexOfKey(requestCode) < 0) {
            return requestCode;
        }
        return generateRequestCode();
    }
}