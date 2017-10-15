package com.example.rxactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.hendraanggrian.app.RxActivity;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

import static com.hendraanggrian.app.RxActivitiesKt.startActivityForResultAsObservable;

public class MainActivity2 extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        button = findViewById(R.id.button);
        setSupportActionBar(toolbar);
    }

    public void onClick(View view) {
        startActivityForResultAsObservable(MainActivity2.this, new Intent(MainActivity2.this, NextActivity.class))
                .subscribe(new Observer<Intent>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull Intent intent) {
                        showSnackbar("onNext");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        showSnackbar("onError:\n" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        RxActivity.INSTANCE.onActivityResult(requestCode, resultCode, data);
    }

    private void showSnackbar(String text) {
        Snackbar snackbar = Snackbar.make(button, text, Snackbar.LENGTH_INDEFINITE)
                .setAction(android.R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
        ((TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text)).setMaxLines(5);
        snackbar.show();
    }
}