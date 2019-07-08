package com.example.simonsay;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.widget.ImageButton;

public  class MusicOfGameButton extends ImageButton
{
    Context m_Context;
    public static MediaPlayer m_mediaPlayer ;

    public static  boolean v_IsPlaying = true;

    public boolean GetIfPlaying()
    {
        return v_IsPlaying;
    }

    public MusicOfGameButton(Context context) {
        super(context);
        m_Context = context;
    }

    public MusicOfGameButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        m_Context = context;
        if(m_mediaPlayer==null)
        {
            m_mediaPlayer = SingletonMediaPlayer.getInstance();
            m_mediaPlayer = MediaPlayer.create(m_Context, R.raw.music_of_game);
            m_mediaPlayer.setLooping(true);
            m_mediaPlayer.start();
        }
    }

    public MusicOfGameButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        m_Context = context;

    }


    public void ResumeOrPauseTheMusic()
    {

        if(v_IsPlaying == true)
        {
            v_IsPlaying = false;
            m_mediaPlayer.pause();

        }
      else
          {
            v_IsPlaying = true;
            m_mediaPlayer.start();
        }


    }

}
