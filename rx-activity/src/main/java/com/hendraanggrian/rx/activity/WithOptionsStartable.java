package com.hendraanggrian.rx.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
interface WithOptionsStartable {

    @TargetApi(16)
    @RequiresApi(16)
    void startActivityForResult(@NonNull Intent intent, int requestCode, @Nullable Bundle options);
}