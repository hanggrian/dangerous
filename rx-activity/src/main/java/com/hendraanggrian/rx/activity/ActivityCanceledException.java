package com.hendraanggrian.rx.activity;

import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class ActivityCanceledException extends Exception {

    @Nullable public final Intent data;

    public ActivityCanceledException(int requestCode, @Nullable Intent data) {
        super("Activity with request code " + requestCode + " is canceled.");
        this.data = data;
    }
}