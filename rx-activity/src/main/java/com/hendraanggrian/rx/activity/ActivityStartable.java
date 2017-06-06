package com.hendraanggrian.rx.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
interface ActivityStartable {

    @NonNull
    PackageManager getPackageManager();

    void startActivityForResult(@NonNull Intent intent, int requestCode);

    @TargetApi(16)
    @RequiresApi(16)
    void startActivityForResult(@NonNull Intent intent, int requestCode, @Nullable Bundle options);
}