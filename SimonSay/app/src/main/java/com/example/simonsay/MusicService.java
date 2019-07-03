package com.example.simonsay;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;

public class MusicService extends AppCompatActivity
{
    Context m_context;
    MediaPlayer m_mediaPlayer = new MediaPlayer();

    public  MusicService(Context i_context)
    {
        m_context = i_context;
    }

    public  void PlayMusicOfTouch(String i_color)
    {
        m_mediaPlayer.release();

        i_color = i_color.substring(i_color.indexOf("/") + 1);


        switch (i_color) {
            case "Blue":
                m_mediaPlayer = MediaPlayer.create(m_context, R.raw.leson1);
                break;

            case "Green":
                m_mediaPlayer = MediaPlayer.create(m_context, R.raw.leson2);
                break;

            case "Yellow":
                m_mediaPlayer = MediaPlayer.create(m_context, R.raw.leson3);
                break;

            case "Red":
                m_mediaPlayer = MediaPlayer.create(m_context, R.raw.leson4);
                break;

        }

        m_mediaPlayer.start();
    }

}