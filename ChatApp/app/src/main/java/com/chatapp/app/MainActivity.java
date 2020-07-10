package com.chatapp.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {
    final webApiFunctions webApiFunctions = new webApiFunctions(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                webApiFunctions.validateSharedPreferences();
            }
        },1000);
    }



    @Override
    protected void onRestart() {
        super.onRestart();
        webApiFunctions.validateSharedPreferences();
    }
}
