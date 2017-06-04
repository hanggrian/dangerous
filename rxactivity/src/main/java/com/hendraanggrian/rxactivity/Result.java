package com.hendraanggrian.rxactivity;

import android.content.Intent;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public final class Result {

    public final int requestCode;
    public final int resultCode;
    public final Intent data;

    Result(int requestCode, int resultCode, Intent data) {
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.data = data;
    }
}