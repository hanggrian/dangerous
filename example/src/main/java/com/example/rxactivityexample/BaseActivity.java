package com.example.rxactivityexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import com.hendraanggrian.bundler.Bundler;
import com.hendraanggrian.rx.activity.RxActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @LayoutRes
    protected abstract int getContentView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        unbinder = ButterKnife.bind(this);
        if (getIntent() != null) {
            Bundler.bind(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        RxActivity.onActivityResult(requestCode, resultCode, data);
    }
}