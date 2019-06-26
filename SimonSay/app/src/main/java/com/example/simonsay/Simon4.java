package com.example.simonsay;

import android.app.Activity;
import android.content.Intent;
import android.content.RestrictionEntry;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Simon4 extends AppCompatActivity implements View.OnClickListener
{
    GameManager gameManager;
    ImageButton greenButton;
    ImageButton yelloButton;
    ImageButton RedButton;
    ImageButton BlueButton;
    TextView numbeOfRequestTv;
    int numberRequest=0;
    int countOfTouch;
    boolean gameover=false;

    ArrayList<eColors.Color> Arr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game4);
        gameManager = new GameManager();
        greenButton = findViewById(R.id.Green);
        greenButton.setOnClickListener(this);
        yelloButton = findViewById(R.id.Yellow);
        yelloButton.setOnClickListener(this);
        RedButton = findViewById(R.id.Red);
        RedButton.setOnClickListener(this);
        BlueButton = findViewById(R.id.Blue);
        BlueButton.setOnClickListener(this);
        numbeOfRequestTv = findViewById(R.id.numberOfRequestTV);

        Arr= new ArrayList<eColors.Color>();
        Arr.add(eColors.Color.Blue);
        Arr.add(eColors.Color.Red);
        Arr.add(eColors.Color.Blue);
        Arr.add(eColors.Color.Green);
        Arr.add(eColors.Color.Green);
        Arr.add(eColors.Color.Red);
        Arr.add(eColors.Color.Blue);
        Arr.add(eColors.Color.Yellow);
        Arr.add(eColors.Color.Green);
        TurnOfComputer(2000,Arr);

    }

    public void TurnOfComputer(final int i_threadSleep , final ArrayList<eColors.Color> i_ArrayRequest)
    {

        numberRequest++;
        numbeOfRequestTv.setText(""+numberRequest);

        new Thread()
        {
            int i=0;
            @Override
            public void run() {
                super.run();
                try{


                        while(i<numberRequest)
                        {
                            Thread.sleep(300);
                            ImageButton button = CheckButtonColor(i_ArrayRequest.get(i));
                            button.setPressed(true);
                            Thread.sleep(i_threadSleep);
                            button.setPressed(false);
                            i++;
                        }

                }catch (InterruptedException e)
                {

                }
            }
        }.start();

        }

    public ImageButton CheckButtonColor (eColors.Color i_color)
    {
      switch(i_color)
      {
          case Red:  return RedButton;
          case Blue:  return BlueButton;
          case Green:  return greenButton;
          case Yellow:  return yelloButton;
          default: return  null;

      }
    }

    @Override
    public void onClick(View v)
    {
        String colorbyindexofTouch =Arr.get(countOfTouch).toString();
        String id=v.getResources().getResourceName(v.getId());
        String nameOfTheId = id.substring(id.indexOf("/")+1);
        if(!Arr.get(countOfTouch).toString().equalsIgnoreCase(nameOfTheId))
        {
            gameover=true;
            GameOver();
        }

        countOfTouch++;

        if(countOfTouch==numberRequest)
        {
            countOfTouch=0;
            TurnOfComputer(2000,Arr);

        }
    }

    public void GameOver()
    {

    }

}





