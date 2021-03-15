package com.example.urlreader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private Logger _logger = Logger.getInstance("URL-MAINACTIVITY");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _logger.log("MainActivity OnCreate");
    }
}
