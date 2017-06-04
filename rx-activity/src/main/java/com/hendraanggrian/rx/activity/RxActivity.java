package com.hendraanggrian.rx.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
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

    private final static SparseArray<EmitterWrapper<?>> REQUESTS = new SparseArray<>();
    private static final int MAX_REQUEST_CODE = 65535;
    private static WeakReference<Random> RANDOM_REQUEST_CODE;

    private RxActivity() {
    }

    @NonNull
    public static Observable<Intent> startForResult(@NonNull final Activity activity, @NonNull Intent intent) {
        return createActivityStarter(new WithoutOptionsStartable() {
            @Override
            public void startActivityForResult(@NonNull Intent intent, int requestCode) {
                activity.startActivityForResult(intent, requestCode);
            }
        }, intent, null);
    }

    @NonNull
    public static Observable<Intent> startForResult(@NonNull final Activity activity, @NonNull Intent intent, @Nullable Bundle options) {
        return createActivityStarter(new WithOptionsStartable() {
            @TargetApi(16)
            @RequiresApi(16)
            @Override
            public void startActivityForResult(@NonNull Intent intent, int requestCode, @Nullable Bundle options) {
                activity.startActivityForResult(intent, requestCode, options);
            }
        }, intent, options);
    }

    @NonNull
    public static Observable<ActivityResult> startForAny(@NonNull final Activity activity, @NonNull Intent intent) {
        return createActivityStarter(new WithoutOptionsStartable() {
            @Override
            public void startActivityForResult(@NonNull Intent intent, int requestCode) {
                activity.startActivityForResult(intent, requestCode);
            }
        }, intent, null);
    }

    @NonNull
    public static Observable<ActivityResult> startForAny(@NonNull final Activity activity, @NonNull Intent intent, @Nullable Bundle options) {
        return createActivityStarter(new WithOptionsStartable() {
            @TargetApi(16)
            @RequiresApi(16)
            @Override
            public void startActivityForResult(@NonNull Intent intent, int requestCode, @Nullable Bundle options) {
                activity.startActivityForResult(intent, requestCode, options);
            }
        }, intent, options);
    }

    @NonNull
    public static Observable<Intent> startForResult(@NonNull final Fragment fragment, @NonNull Intent intent) {
        return createActivityStarter(new WithoutOptionsStartable() {
            @Override
            public void startActivityForResult(@NonNull Intent intent, int requestCode) {
                fragment.startActivityForResult(intent, requestCode);
            }
        }, intent, null);
    }

    @NonNull
    public static Observable<Intent> startForResult(@NonNull final Fragment fragment, @NonNull Intent intent, @Nullable Bundle options) {
        return createActivityStarter(new WithOptionsStartable() {
            @TargetApi(16)
            @RequiresApi(16)
            @Override
            public void startActivityForResult(@NonNull Intent intent, int requestCode, @Nullable Bundle options) {
                fragment.startActivityForResult(intent, requestCode, options);
            }
        }, intent, options);
    }

    @NonNull
    public static Observable<ActivityResult> startForAny(@NonNull final Fragment fragment, @NonNull Intent intent) {
        return createActivityStarter(new WithoutOptionsStartable() {
            @Override
            public void startActivityForResult(@NonNull Intent intent, int requestCode) {
                fragment.startActivityForResult(intent, requestCode);
            }
        }, intent, null);
    }

    @NonNull
    public static Observable<ActivityResult> startForAny(@NonNull final Fragment fragment, @NonNull Intent intent, @Nullable Bundle options) {
        return createActivityStarter(new WithOptionsStartable() {
            @TargetApi(16)
            @RequiresApi(16)
            @Override
            public void startActivityForResult(@NonNull Intent intent, int requestCode, @Nullable Bundle options) {
                fragment.startActivityForResult(intent, requestCode, options);
            }
        }, intent, options);
    }

    @NonNull
    public static Observable<Intent> startForResult(@NonNull final android.support.v4.app.Fragment fragment, @NonNull Intent intent) {
        return createActivityStarter(new WithoutOptionsStartable() {
            @Override
            public void startActivityForResult(@NonNull Intent intent, int requestCode) {
                fragment.startActivityForResult(intent, requestCode);
            }
        }, intent, null);
    }

    @NonNull
    public static Observable<Intent> startForResult(@NonNull final android.support.v4.app.Fragment fragment, @NonNull Intent intent, @Nullable Bundle options) {
        return createActivityStarter(new WithOptionsStartable() {
            @TargetApi(16)
            @RequiresApi(16)
            @Override
            public void startActivityForResult(@NonNull Intent intent, int requestCode, @Nullable Bundle options) {
                fragment.startActivityForResult(intent, requestCode, options);
            }
        }, intent, options);
    }

    @NonNull
    public static Observable<ActivityResult> startForAny(@NonNull final android.support.v4.app.Fragment fragment, @NonNull Intent intent) {
        return createActivityStarter(new WithoutOptionsStartable() {
            @Override
            public void startActivityForResult(@NonNull Intent intent, int requestCode) {
                fragment.startActivityForResult(intent, requestCode);
            }
        }, intent, null);
    }

    @NonNull
    public static Observable<ActivityResult> startForAny(@NonNull final android.support.v4.app.Fragment fragment, @NonNull Intent intent, @Nullable Bundle options) {
        return createActivityStarter(new WithOptionsStartable() {
            @TargetApi(16)
            @RequiresApi(16)
            @Override
            public void startActivityForResult(@NonNull Intent intent, int requestCode, @Nullable Bundle options) {
                fragment.startActivityForResult(intent, requestCode, options);
            }
        }, intent, options);
    }

    @NonNull
    private static <T> Observable<T> createActivityStarter(@NonNull final Object startable, @NonNull final Intent intent, @Nullable final Bundle options) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(@io.reactivex.annotations.NonNull ObservableEmitter<T> e) throws Exception {
                int requestCode = generateRandom();
                boolean isSimple = startable instanceof WithoutOptionsStartable;
                REQUESTS.append(requestCode, new EmitterWrapper<>(isSimple, e));
                if (isSimple) {
                    ((WithoutOptionsStartable) startable).startActivityForResult(intent, requestCode);
                } else if (startable instanceof WithOptionsStartable && Build.VERSION.SDK_INT >= 16) {
                    ((WithOptionsStartable) startable).startActivityForResult(intent, requestCode, options);
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (REQUESTS.indexOfKey(requestCode) > -1) {
            EmitterWrapper wrapper = REQUESTS.get(requestCode);
            if (!wrapper.isSimple) {
                wrapper.emitter.onNext(new ActivityResult(requestCode, resultCode, data));
            } else {
                if (resultCode == Activity.RESULT_OK) {
                    wrapper.emitter.onNext(data);
                } else {
                    wrapper.emitter.onError(new ActivityCanceledException());
                }
            }
            wrapper.emitter.onComplete();
            REQUESTS.remove(requestCode);
        }
    }

    private static int generateRandom() {
        if (RANDOM_REQUEST_CODE == null) {
            RANDOM_REQUEST_CODE = new WeakReference<>(new Random());
        }
        final int requestCode;
        Random random = RANDOM_REQUEST_CODE.get();
        if (random != null) {
            requestCode = random.nextInt(MAX_REQUEST_CODE);
        } else {
            random = new Random();
            requestCode = random.nextInt(MAX_REQUEST_CODE);
            RANDOM_REQUEST_CODE = new WeakReference<>(random);
        }
        if (REQUESTS.indexOfKey(requestCode) < 0) {
            return requestCode;
        }
        return generateRandom();
    }
}