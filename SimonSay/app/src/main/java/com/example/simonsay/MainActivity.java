package com.example.simonsay;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.hitomi.cmlibrary.CircleMenu;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton goIB;
    CoordinatorLayout menuLayotIB;
    CircleMenu circleMenu;
    FloatingActionButton menuFab, scoreTavleB, subMenu2;
    Animation fbOpen, fbClose, rotatClWise, rotatAntiClk, fromLeft, fromRightMenue;
    boolean Isopen = false;
    Button gameOverB;
    LinearLayout circleLayout;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int numberSetRequest =0;
        int level =0;
        menuLayotIB = findViewById(R.id.menuLayot);
        goIB= findViewById(R.id.goIB);
        scoreTavleB = findViewById(R.id.subMainBtn1);
        menuFab = findViewById(R.id.mainBtn);
        subMenu2=findViewById(R.id.subMainBtn2);


        fbOpen = AnimationUtils.loadAnimation(this, R.anim.fb_open);
        fbClose = AnimationUtils.loadAnimation(this, R.anim.fb_close);
        rotatClWise = AnimationUtils.loadAnimation(this, R.anim.rotate_clockewise);
        rotatAntiClk = AnimationUtils.loadAnimation(this, R.anim.rotate_anticlkwise);
        fromLeft = AnimationUtils.loadAnimation(this, R.anim.from_left);
        fromRightMenue = AnimationUtils.loadAnimation(this, R.anim.from_right_menu);

        menuLayotIB.startAnimation(fromLeft);
        goIB.startAnimation(fromRightMenue);


        menuFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Isopen)
                {
                    scoreTavleB.startAnimation(fbClose);
                    subMenu2.startAnimation(fbClose);
                    menuFab.startAnimation(rotatAntiClk);
                    scoreTavleB.setClickable(false);
                    subMenu2.setClickable(false);
                    Isopen =false;

                }
                else
                {
                    scoreTavleB.startAnimation(fbOpen);
                    subMenu2.startAnimation(fbOpen);
                    menuFab.startAnimation(rotatClWise);
                    scoreTavleB.setClickable(true);
                    subMenu2.setClickable(true);
                    Isopen =true;


                }
            }
        });


        RelativeLayout relativeLayout = findViewById(R.id.mainRelativeLayout);

        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();


        goIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LevelsActivity.class);
                startActivity(intent);
            }
        });

        scoreTavleB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(MainActivity.this,TableScoreActivity.class);
                startActivity(intent4);
            }
        });

        MusicOfGameButton musicOfGame = findViewById(R.id.MusciButtonId);
        musicOfGame.setOnClickListener(this);



    }

    @Override
    public void onClick(View v)
    {
        if(v instanceof MusicOfGameButton)
        {
            ((MusicOfGameButton) v).ResumeOrPauseTheMusic();
        }
    }
}
