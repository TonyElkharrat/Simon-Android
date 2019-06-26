package com.example.simonsay;

import android.app.Activity;
import android.content.Intent;
import android.content.RestrictionEntry;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class SimonGame extends AppCompatActivity implements View.OnClickListener
{
    private ImageButton greenButton;
    private   ImageButton yelloButton;
    private ImageButton RedButton;
    private ImageButton BlueButton;
    private TextView numbeOfRequestTv;
    private  RelativeLayout pannel;
    private  ArrayList<View> ListOfChildren;
    private int numberRequest=0;
    private int countOfTouch;
    private boolean gameover=false;
    private int m_SleepOfThread=0;
    private ArrayList<eColors.Color> ArrayOfColor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game4);
        Initialize();
        GameManager.CreateLevel(ArrayOfColor);
        m_SleepOfThread=800;
        TurnOfComputer();
    }

    public void Initialize()
    {
        greenButton = findViewById(R.id.Green);
        greenButton.setOnClickListener(this);
        yelloButton = findViewById(R.id.Yellow);
        yelloButton.setOnClickListener(this);
        RedButton = findViewById(R.id.Red);
        RedButton.setOnClickListener(this);
        BlueButton = findViewById(R.id.Blue);
        BlueButton.setOnClickListener(this);
        numbeOfRequestTv = findViewById(R.id.numberOfRequestTV);
        ArrayOfColor= new ArrayList<eColors.Color>();
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Pacifico.ttf");
        numbeOfRequestTv.setTypeface(typeface);
        pannel = findViewById(R.id.mainRelativeLayout);
        ListOfChildren = new ArrayList<>();
        ListOfChildren = getAllChildren(pannel);

    }

    public void TurnOfComputer()
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
                            Thread.sleep(100);
                            ImageButton button = CheckButtonColor(ArrayOfColor.get(i),ListOfChildren);
                            button.setPressed(true);
                            Thread.sleep(m_SleepOfThread);
                            button.setPressed(false);
                            i++;
                        }

                        numbeOfRequestTv.setText("Your Turn");
                        Thread.sleep(200);


                }catch (InterruptedException e)
                {

                }
            }
        }.start();

        }

    public ImageButton CheckButtonColor (eColors.Color i_color, ArrayList<View> i_ListOfAllChildren)
    {

        for (int i = 0; i < i_ListOfAllChildren.size(); i++)
        {
            View childOfRelativeLayout = i_ListOfAllChildren.get(i);

                    if (childOfRelativeLayout instanceof ImageButton)
                    {
                       if(CheckIdEqualToView(i_color,childOfRelativeLayout) )
                       {
                           return (ImageButton)childOfRelativeLayout;
                       }
                    }

        }
        return null;
    }

    private ArrayList<View> getAllChildren(View v) {

        if (!(v instanceof ViewGroup)) {
            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            return viewArrayList;
        }

        ArrayList<View> result = new ArrayList<View>();

        ViewGroup vg = (ViewGroup) v;
        for (int i = 0; i < vg.getChildCount(); i++) {

            View child = vg.getChildAt(i);

            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            viewArrayList.addAll(getAllChildren(child));

            result.addAll(viewArrayList);
        }
        return result;
    }

    @Override
    public void onClick(View v)
    {

        if(!CheckIdEqualToView(ArrayOfColor.get(countOfTouch),v))
        {
            gameover=true;
            GameOver();
        }

        countOfTouch++;
        if(countOfTouch==numberRequest)
        {
            countOfTouch=0;
            TurnOfComputer();

        }
    }


    public boolean CheckIdEqualToView(eColors.Color i_color, View buttonColor)
    {
        boolean v_isEqual = false;
        String id=buttonColor.getResources().getResourceName(buttonColor.getId());
        String nameOfTheId = id.substring(id.indexOf("/")+1);
        String s =i_color.toString();
        if(s.equalsIgnoreCase(nameOfTheId))
        {
            v_isEqual = true;
        }
        return v_isEqual;

    }

    public void PlayMusicOfTouch(String i_color)
    {
        switch (i_color)
        {
            case "Red":
        }
    }

    public void GameOver()
    {

    }

}





