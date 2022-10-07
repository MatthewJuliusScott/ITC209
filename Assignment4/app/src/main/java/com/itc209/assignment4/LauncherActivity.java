package com.itc209.assignment4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        Thread loading = new Thread() {
            public void run() {
                try {
                    sleep(1500);
                    Intent main = new Intent(LauncherActivity.this,MainActivity.class);
                    startActivity(main);
                    finish();
                }

                catch (Exception e) {
                    e.printStackTrace();
                }

                finally {
                    finish();
                }
            }
        };

        loading.start();
    }
}