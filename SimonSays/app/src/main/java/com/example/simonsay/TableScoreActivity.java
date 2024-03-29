package com.example.simonsay;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

public class TableScoreActivity extends AppCompatActivity
{
    final String tableScore = "Record_Score";
    final String CREATE_TABLE_CMD=" CREATE TABLE IF NOT EXISTS "+tableScore+ "(id INTEGER PRIMARY KEY AUTOINCREMENT, Date TEXT, Level TEXT, Score TEXT);";
    SQLiteDatabase database;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_table);
        ImageView star1= findViewById(R.id.star1);
        ImageView star2= findViewById(R.id.star2);
        Animation starScaleAnim= AnimationUtils.loadAnimation(this, R.anim.zoomout1);
        star1.startAnimation(starScaleAnim);
        star2.startAnimation(starScaleAnim);
        final ListView tableScore = findViewById(R.id.RecordTableId);

        database = openOrCreateDatabase("dataOfScore",MODE_PRIVATE,null);
        database.execSQL(CREATE_TABLE_CMD);

        adapter = new ArrayAdapter<>(this,R.layout.list_black_text,R.id.list_content);
        tableScore.setAdapter(adapter);

       // ImageView trophy = findViewById(R.id.TrophyId);


        ImageButton buttonFilter = findViewById(R.id.leaderIB);
        ImageButton allScoreButton = findViewById(R.id.allScorsIB);

        buttonFilter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                final ArrayAdapter<String> adapterOfBestScore = new ArrayAdapter<>(TableScoreActivity.this,R.layout.list_black_text,R.id.list_content);

                Cursor cursor = database.query("Record_Score", new String[] {"Date", "Level", "Score", "max(Score) as max_Score"},null, null, "Level", null, null);

                    MakeTable(cursor,adapterOfBestScore,"max_Score");
                    adapterOfBestScore.notifyDataSetChanged();
                    tableScore.setAdapter(adapterOfBestScore);
                }


        });

    allScoreButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        tableScore.setAdapter(adapter);
    }
});
    ShowTable();

    }

public void ShowTable()
{
    Cursor cursor =database.query(tableScore,null,null,null,null,null,null);
    MakeTable(cursor,adapter,"Score");
    adapter.notifyDataSetChanged();
}

public void MakeTable(Cursor cursor, ArrayAdapter<String> i_Adapater,String i_Score )
{
    int scoreindex = cursor.getColumnIndex(i_Score);
    int levelindex = cursor.getColumnIndex("Level");
    int dateindex = cursor.getColumnIndex("Date");
    String addSpace;
    while(cursor.moveToNext())
    {

        String row = cursor.getString(dateindex)+"               "+ cursor.getString(levelindex) ;

        if(row.contains("Easy"))
        {
            row= row+"                    ";
        }
        else  if(row.contains("Medium"))
        {
            row= row+"             ";
        }
        else
        {
            row= row +"                    ";
        }
       row = row + cursor.getInt(scoreindex);
        i_Adapater.add(row);

    }
    cursor.close();
}

}
