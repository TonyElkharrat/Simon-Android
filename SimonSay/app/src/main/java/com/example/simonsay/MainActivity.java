package com.example.simonsay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int numberSetRequest =0;
        int level =0;
        Intent Simon4 = new Intent(MainActivity.this, SimonGame.class);
        startActivity(Simon4);

    }
}
