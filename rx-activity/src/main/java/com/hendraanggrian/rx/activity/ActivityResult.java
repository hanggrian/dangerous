package com.hendraanggrian.rx.activity;

import android.content.Intent;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public final class ActivityResult {

    public final int requestCode;
    public final int resultCode;
    public final Intent data;

    ActivityResult(int requestCode, int resultCode, Intent data) {
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.data = data;
    }
}