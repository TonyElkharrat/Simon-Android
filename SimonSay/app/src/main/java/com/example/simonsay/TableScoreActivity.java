package com.example.simonsay;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.Date;

public class TableScoreActivity extends AppCompatActivity
{
    final String tableScore = "Record_Score";
    final String CREATE_TABLE_CMD=" CREATE TABLE IF NOT EXISTS "+tableScore+ "(id INTEGER PRIMARY KEY AUTOINCREMENT, Date TEXT, Level TEXT, Score TEXT);";
    SQLiteDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_table);
        ListView tableScore = findViewById(R.id.RecordTableId);
        database = openOrCreateDatabase("dataOfScore",MODE_PRIVATE,null);
        database.execSQL(CREATE_TABLE_CMD);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
        tableScore.setAdapter(adapter);
        ContentValues contentValues = new ContentValues();
        CreateScore(adapter,contentValues,eLevel.Level.Commando,4);
        Button buttonFilter = findViewById(R.id.filterButtonId);
        buttonFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder =  new AlertDialog.Builder(TableScoreActivity.this);
                builder.setTitle("Choose a Filter").setItems(new String[]{eLevel.Level.Easy.toString(),eLevel.Level.Medium.toString(),eLevel.Level.Commando.toString()},
                        new FilterLevelListener(TableScoreActivity.this)).setPositiveButton("Save", new FilterLevelListener(TableScoreActivity.this)).show();
            }
        });


    }

    public void CreateScore ( ArrayAdapter<String> adapter,ContentValues i_contentValues,eLevel.Level i_Level ,int i_Score)
    {
        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String todayString = formatter.format(todayDate);

        i_contentValues.put("Date",todayString);
        i_contentValues.put("Score",i_Score);
        i_contentValues.put("Level",i_Level.toString());

        database.insert(tableScore,null,i_contentValues);

        Cursor cursor =database.query(tableScore,null,null,null,null,null,null);
        int dateIndex = cursor.getColumnIndex("Date");
        int scoreIndex = cursor.getColumnIndex("Score");
        int levelIndex = cursor.getColumnIndex("Level");

        while(cursor.moveToNext())
        {
            String date = cursor.getString(dateIndex) + "         " + cursor.getString(levelIndex)+"                "+ cursor.getInt(scoreIndex);
            adapter.add(date);
        }
        adapter.notifyDataSetChanged();

    }

}
