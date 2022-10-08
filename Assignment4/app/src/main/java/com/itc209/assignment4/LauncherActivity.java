package com.itc209.assignment4;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        Thread loading = new Thread(() -> {
            try {
                Thread.sleep(1500);
                Intent main = new Intent(LauncherActivity.this, MainActivity.class);
                startActivity(main);
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                finish();
            }
        });

        loading.start();
    }
}