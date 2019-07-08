package com.example.simonsay;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;

public class MusicButtonsTouch extends AppCompatActivity {
    Context m_context;
    MediaPlayer m_mediaPlayer = new MediaPlayer();

    public MusicButtonsTouch(Context i_context) {
        m_context = i_context;
    }

    public void PlayMusicOfTouch(String i_color) {
        m_mediaPlayer.release();

        i_color = i_color.substring(i_color.indexOf("/") + 1);


        switch (i_color) {
            case "Blue":
                m_mediaPlayer = MediaPlayer.create(m_context, R.raw.lesson1);
                break;

            case "Green":
                m_mediaPlayer = MediaPlayer.create(m_context, R.raw.lesson5);
                break;

            case "Yellow":
                m_mediaPlayer = MediaPlayer.create(m_context, R.raw.lesson6);
                break;

            case "Red":
                m_mediaPlayer = MediaPlayer.create(m_context, R.raw.lesson7);
                break;
            case "Purpel":
                m_mediaPlayer = MediaPlayer.create(m_context, R.raw.lesson8);
                break;
            case "DarkGreen":
                m_mediaPlayer = MediaPlayer.create(m_context, R.raw.lesson9);
                break;
            case "Orange":
                m_mediaPlayer = MediaPlayer.create(m_context, R.raw.lesson11);
                break;
            case "Pink":
                m_mediaPlayer = MediaPlayer.create(m_context, R.raw.lesson12);
                break;

        }

        m_mediaPlayer.start();
    }

    public void PlayMusic(String i_Music)
    {
        m_mediaPlayer.release();

        if(i_Music.equalsIgnoreCase("Game Over"))
        {
            m_mediaPlayer = MediaPlayer.create(m_context, R.raw.gane_over_music);

        }

        m_mediaPlayer.start();

    }
}
