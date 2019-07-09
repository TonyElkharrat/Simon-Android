package com.example.simonsay;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class LevelsActivity extends AppCompatActivity {

     ArrayList<String> arrayTransfer;
    ImageButton easyBtn,mediumBtn,HardBtn;
    SharedPreferences m_sharedPreferences;
    ImageView mediumLock,hardLock;
    TextView medium,hard;
    TextView feelingGood,feelingexpert;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_list);

        ImageButton easyBtn= findViewById(R.id.easyButtonId);
         mediumBtn= findViewById(R.id.meduimButtonID);
         HardBtn= findViewById(R.id.hardButtonID);
        LinearLayout easyWords = findViewById(R.id.easyWords);
        LinearLayout mediumWords = findViewById(R.id.mediumWords);
        LinearLayout hardWords = findViewById(R.id.hardWords);
         mediumLock = findViewById(R.id.lock_medium);
         hardLock = findViewById(R.id.lock_hard);
         medium = findViewById(R.id.mediumTV);
         hard = findViewById(R.id.hardTV);
         feelingGood = findViewById(R.id.feelingGoodTV);
         feelingexpert = findViewById(R.id.feelingExpertTV);

        m_sharedPreferences = getSharedPreferences("details",MODE_PRIVATE);



        Animation fromRight= AnimationUtils.loadAnimation(this, R.anim.from_right);
        easyBtn.startAnimation(fromRight);
        HardBtn.startAnimation(fromRight);
        easyWords.startAnimation(fromRight);
        hardWords.startAnimation(fromRight);

        Animation fromLeft= AnimationUtils.loadAnimation(this, R.anim.from_left);
        mediumBtn.startAnimation(fromLeft);
        mediumWords.startAnimation(fromLeft);


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

        HardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuildLevelAndThreadTime(eLevel.Level.Hard,"500");
            }
        });

    }

    public void BuildLevelAndThreadTime(eLevel.Level i_Level, String i_ThreadTime)
    {
        arrayTransfer = new ArrayList<String>();
        arrayTransfer.add(i_Level.toString());
        arrayTransfer.add("800");
        Intent SimonIntent = new Intent(LevelsActivity.this, SimonGameActivity.class);
        SimonIntent.putExtra("level",arrayTransfer);
        startActivity(SimonIntent);
       // overridePendingTransition(R.anim.from_left, R.anim.fade_exit);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        int bestScore = m_sharedPreferences.getInt("record_of_the_user",0);

        if(bestScore>=3)
        {
            MakeEnable(mediumBtn, getResources().getDrawable(R.drawable.simon_medium));
            mediumLock.setVisibility(View.INVISIBLE);
            feelingGood.setTextColor(Color.WHITE);
            medium.setTextColor(Color.WHITE);
        }

        else
        {
            medium.setClickable(false);
        }

            if(bestScore>=5)
        {
            MakeEnable(HardBtn,getResources().getDrawable(R.drawable.simon_hard));
            hardLock.setVisibility(View.INVISIBLE);
            feelingexpert.setTextColor(Color.WHITE);
            hard.setTextColor(Color.WHITE);


        }
            else
            {
                hard.setClickable(false);

            }


    }
    public void MakeEnable(ImageView i_ImageToDisable , Drawable i_ImageToChange)
    {
        i_ImageToDisable.setClickable(true);
        i_ImageToDisable.setBackground(i_ImageToChange);
    }
    @Override
    public void finish()
    {
        super.finish();
        overridePendingTransition(R.anim.fade_enter, R.anim.fade_exit);
    }
}