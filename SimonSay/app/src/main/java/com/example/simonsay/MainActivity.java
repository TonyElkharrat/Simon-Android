package com.example.simonsay;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences ;

        sharedPreferences = getSharedPreferences("details",MODE_PRIVATE);
        ArrayList<String> arr = new ArrayList<>();
        arr.add(eLevel.Level.Medium.toString());
        arr.add("800");

        Intent SimonIntent = new Intent(MainActivity.this, SimonGameActivity.class);
        SimonIntent.putExtra("level",arr);
        startActivity(SimonIntent);
//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
//        View layoutView = getLayoutInflater().inflate(R.layout.game_over_dialog, null);
//        dialogBuilder.setView(layoutView);
//        AlertDialog  alertDialog = dialogBuilder.create();
//        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogSlide;

//        alertDialog.show();
//        Intent tableScoreActivity = new Intent(MainActivity.this, TableScoreActivity.class);
//        startActivity(tableScoreActivity);
    }
}
