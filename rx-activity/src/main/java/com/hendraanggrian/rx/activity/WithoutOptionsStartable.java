package com.hendraanggrian.rx.activity;

import android.content.Intent;
import android.support.annotation.NonNull;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
interface WithoutOptionsStartable {

    void startActivityForResult(@NonNull Intent intent, int requestCode);
}