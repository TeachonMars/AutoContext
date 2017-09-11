package com.teachonmars.module.autoContext.appDemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.teachonmars.autoinit.appDemo.R;


public class DemoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        TextView infoView = (TextView) findViewById(R.id.infoView);
        infoView.setText((getApplicationContext() == AutoInitTester.appContext)
                ? R.string.wellInit
                : R.string.badInit);
    }
}
