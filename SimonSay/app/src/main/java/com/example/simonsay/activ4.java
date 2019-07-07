package com.example.simonsay;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

public class activ4 extends AppCompatActivity {
    ImageButton saveBtn;
    /*ImageButton shareBtn;*/
    ImageButton returnBtn;
    ImageButton homeBtn;
    ImageView simonIV;
    Animation fromBottom, fromRight, fromTop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over_dialog);

        saveBtn= findViewById(R.id.SaveButton);
        simonIV= findViewById(R.id.imageOfSimon);
        returnBtn= findViewById(R.id.restartGame);
        homeBtn= findViewById(R.id.HomeButton);
        fromTop= AnimationUtils.loadAnimation(this, R.anim.from_top);
        simonIV.startAnimation(fromTop);
        fromBottom= AnimationUtils.loadAnimation(this, R.anim.from_buttom);
        saveBtn.startAnimation(fromBottom);


        fromRight= AnimationUtils.loadAnimation(this, R.anim.from_right);
        homeBtn.startAnimation(fromRight);
        returnBtn.startAnimation(fromRight);

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_enter, R.anim.fade_exit);
    }
}
