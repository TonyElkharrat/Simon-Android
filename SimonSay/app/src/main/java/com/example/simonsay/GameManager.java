package com.example.simonsay;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class GameManager
{
    boolean gameover=false;

    static ArrayList<eColors.Color> Arr ;
    static private  ArrayList<View> ListOfChildren;
    static int m_SleepOfThread=1000;
    static int numberRequest=0;

    public GameManager(RelativeLayout i_pannel)
    {
        Arr= new ArrayList<eColors.Color>();
        numberRequest=0;
        ListOfChildren= new ArrayList<View>();
        ListOfChildren = getAllChildren(i_pannel);
    }

    public  void CreateLevel()
    {
        Arr.add(eColors.Color.Blue);
        Arr.add(eColors.Color.Red);
        Arr.add(eColors.Color.Blue);
        Arr.add(eColors.Color.Green);
        Arr.add(eColors.Color.Green);
        Arr.add(eColors.Color.Red);
        Arr.add(eColors.Color.Blue);
        Arr.add(eColors.Color.Yellow);
        Arr.add(eColors.Color.Green);
    }
    public void TurnOfComputer( final TextView numbeOfRequestTv)
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
                        ImageButton button = GameManager.CheckButtonColor(Arr.get(i),ListOfChildren);
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

    public static ImageButton CheckButtonColor (eColors.Color i_color, ArrayList<View> i_ListOfAllChildren)
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

    public  ArrayList<View> getAllChildren(View v) {

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

    public static boolean CheckIdEqualToView(eColors.Color i_color, View buttonColor)
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

}
