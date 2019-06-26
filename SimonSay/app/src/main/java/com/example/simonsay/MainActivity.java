package com.example.simonsay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.logging.Level;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int numberSetRequest =0;
        int level =0;
        Intent Simon4 = new Intent(MainActivity.this, Simon4.class);
        startActivity(Simon4);

    }
}
