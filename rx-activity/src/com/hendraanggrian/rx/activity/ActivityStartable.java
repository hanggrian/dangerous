package com.hendraanggrian.rx.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
abstract class ActivityStartable {

    @NonNull
    abstract PackageManager getPackageManager();

    abstract void startActivityForResult(@NonNull Intent intent, int requestCode);

    @TargetApi(16)
    @RequiresApi(16)
    abstract void startActivityForResult(@NonNull Intent intent, int requestCode, @Nullable Bundle options);

    @NonNull
    static ActivityStartable fromActivity(@NonNull final Activity activity) {
        return new ActivityStartable() {
            @NonNull
            @Override
            public PackageManager getPackageManager() {
                return activity.getPackageManager();
            }

            @Override
            public void startActivityForResult(@NonNull Intent intent, int requestCode) {
                activity.startActivityForResult(intent, requestCode);
            }

            @TargetApi(16)
            @RequiresApi(16)
            @Override
            public void startActivityForResult(@NonNull Intent intent, int requestCode, @Nullable Bundle options) {
                activity.startActivityForResult(intent, requestCode, options);
            }
        };
    }

    @NonNull
    static ActivityStartable fromFragment(@NonNull final Fragment fragment) {
        return new ActivityStartable() {
            @NonNull
            @Override
            public PackageManager getPackageManager() {
                if (Build.VERSION.SDK_INT >= 23) {
                    return fragment.getContext().getPackageManager();
                } else if (Build.VERSION.SDK_INT >= 11) {
                    return fragment.getActivity().getPackageManager();
                } else {
                    throw new RuntimeException("Using RxActivity in Fragment requires API level 11 or higher. Use support Fragment or increase sdk min to 11.");
                }
            }

            @Override
            public void startActivityForResult(@NonNull Intent intent, int requestCode) {
                if (Build.VERSION.SDK_INT >= 11) {
                    fragment.startActivityForResult(intent, requestCode);
                } else {
                    throw new RuntimeException("Using RxActivity in Fragment requires API level 11 or higher. Use support Fragment or increase sdk min to 11.");
                }
            }

            @TargetApi(16)
            @RequiresApi(16)
            @Override
            public void startActivityForResult(@NonNull Intent intent, int requestCode, @Nullable Bundle options) {
                fragment.startActivityForResult(intent, requestCode, options);
            }
        };
    }

    @NonNull
    static ActivityStartable fromSupportFragment(@NonNull final android.support.v4.app.Fragment fragment) {
        return new ActivityStartable() {
            @NonNull
            @Override
            public PackageManager getPackageManager() {
                return fragment.getContext().getPackageManager();
            }

            @Override
            public void startActivityForResult(@NonNull Intent intent, int requestCode) {
                fragment.startActivityForResult(intent, requestCode);
            }

            @TargetApi(16)
            @RequiresApi(16)
            @Override
            public void startActivityForResult(@NonNull Intent intent, int requestCode, @Nullable Bundle options) {
                fragment.startActivityForResult(intent, requestCode, options);
            }
        };
    }
}