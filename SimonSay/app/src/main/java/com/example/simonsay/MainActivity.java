package com.example.simonsay;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Array;
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
        arr.add(eLevel.Level.Commando.toString());
        arr.add(eLevel.Level.Commando.toString());

        Intent SimonIntent = new Intent(MainActivity.this, SimonGameActivity.class);
//        SimonIntent.putExtra("level",arr);
        startActivity(SimonIntent);

//        Intent tableScoreActivity = new Intent(MainActivity.this, TableScoreActivity.class);
//        startActivity(tableScoreActivity);
    }
}
