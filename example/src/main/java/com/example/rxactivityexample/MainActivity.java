package com.example.rxactivityexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.hendraanggrian.rx.activity.RxActivity;
import com.hendraanggrian.support.utils.widget.Toasts;

import butterknife.BindView;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.button1) Button button1;
    @BindView(R.id.button2) Button button2;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);

        /*RxActivity.startForResult(this, new Intent(this, ExampleActivity.class))
                .subscribe(data -> {
                    Toasts.showShort(this, data.getStringExtra("TEST"));
                });*/

        RxActivity.startForAny(this, new Intent(this, ExampleActivity.class))
                .subscribe(activityResult -> {
                    Toasts.showShort(this, String.valueOf(activityResult.resultCode));
                    Toasts.showShort(this, activityResult.data.getStringExtra("TEST"));
                });
    }
}