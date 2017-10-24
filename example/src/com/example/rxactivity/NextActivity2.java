package com.example.rxactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.hendraanggrian.bundler.BindExtra;
import com.hendraanggrian.bundler.Bundler;

import static java.lang.String.format;

public class NextActivity2 extends AppCompatActivity implements View.OnClickListener {

    public static final int RESULT_CUSTOM = 9152;

    private Toolbar toolbar;
    private Button okButton;
    private Button canceledButton;
    private Button customButton;

    @BindExtra String from;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        toolbar = findViewById(R.id.toolbar);
        okButton = findViewById(R.id.okButton);
        canceledButton = findViewById(R.id.canceledButton);
        customButton = findViewById(R.id.customButton);

        Bundler.bindExtras(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(format("from %s", from));

        okButton.setOnClickListener(this);
        canceledButton.setOnClickListener(this);
        customButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.okButton:
                setResult(RESULT_OK, new Intent());
                break;
            case R.id.canceledButton:
                setResult(RESULT_CANCELED, new Intent());
                break;
            case R.id.customButton:
                setResult(RESULT_CUSTOM, new Intent());
                break;
        }
        finish();
    }
}