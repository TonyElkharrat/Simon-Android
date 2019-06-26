package com.example.simonsay;

import java.util.ArrayList;

public class GameManager
{
    boolean gameover=false;
    public static void CreateLevel(ArrayList<eColors.Color> Arr)
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
}
