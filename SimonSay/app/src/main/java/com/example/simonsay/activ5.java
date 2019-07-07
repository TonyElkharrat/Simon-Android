package com.example.simonsay;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class activ5 extends AppCompatActivity {

    ImageView star1, star2;
    Animation starScaleAnim;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_table);


        star1= findViewById(R.id.star1);
        star2= findViewById(R.id.star2);
        starScaleAnim= AnimationUtils.loadAnimation(this, R.anim.zoomout1);
        star1.startAnimation(starScaleAnim);
        star2.startAnimation(starScaleAnim);

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_enter, R.anim.fade_exit);
    }
}
