package com.example.simonsay;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import java.util.ArrayList;

public class LevelsActivity extends AppCompatActivity {

    final ArrayList<String> arrayTransfer = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_list);

        ImageButton easyBtn= findViewById(R.id.easyButtonId);
        ImageButton mediumBtn= findViewById(R.id.meduimButtonID);
        ImageButton hardBtn= findViewById(R.id.hardButtonID);


        Animation fromRight= AnimationUtils.loadAnimation(this, R.anim.from_right);
        easyBtn.startAnimation(fromRight);
        hardBtn.startAnimation(fromRight);

        Animation fromLeft= AnimationUtils.loadAnimation(this, R.anim.from_left);
        mediumBtn.startAnimation(fromLeft);


        easyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                BuildLevelAndThreadTime(eLevel.Level.Easy,"1000");
            }
        });
        mediumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuildLevelAndThreadTime(eLevel.Level.Medium,"700");
            }
        });

        hardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuildLevelAndThreadTime(eLevel.Level.Hard,"500");
            }
        });

    }

    public void BuildLevelAndThreadTime(eLevel.Level i_Level, String i_ThreadTime)
    {
        arrayTransfer.add(i_Level.toString());
        arrayTransfer.add("800");
        Intent SimonIntent = new Intent(LevelsActivity.this, SimonGameActivity.class);
        SimonIntent.putExtra("level",arrayTransfer);
        startActivity(SimonIntent);
       // overridePendingTransition(R.anim.from_left, R.anim.fade_exit);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_enter, R.anim.fade_exit);
    }
}