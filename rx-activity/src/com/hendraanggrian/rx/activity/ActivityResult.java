package com.hendraanggrian.rx.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

/**
 * Data class representing result of an activity started with RxActivity's startForResult(...).
 *
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public final class ActivityResult {

    private final int requestCode;
    private final int resultCode;
    @NonNull private final Intent data;

    ActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.data = data;
    }

    @Override
    public String toString() {
        final Object resultCode;
        if (this.resultCode == Activity.RESULT_OK) {
            resultCode = "RESULT_OK";
        } else if (this.resultCode == Activity.RESULT_CANCELED) {
            resultCode = "RESULT_CANCELED";
        } else {
            resultCode = this.resultCode;
        }
        return String.format("ActivityResult[requestCode=%s, resultCode=%s]", requestCode, resultCode);
    }

    public int getRequestCode() {
        return requestCode;
    }

    public int getResultCode() {
        return resultCode;
    }

    @NonNull
    public Intent getData() {
        return data;
    }
}