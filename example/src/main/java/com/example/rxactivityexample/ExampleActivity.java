package com.example.rxactivityexample;

import android.content.Intent;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class ExampleActivity extends BaseActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK, new Intent().putExtra("TEST", "Hello world"));
        super.onBackPressed();
    }
}