package com.example.simonsay;

import android.media.MediaPlayer;

public class SingletonMediaPlayer
{
        private static MediaPlayer m_uniqueInstance;

        public static MediaPlayer  getInstance()
        {
            if (m_uniqueInstance == null)
            {
                m_uniqueInstance = new MediaPlayer();
            }

            return m_uniqueInstance ;

        }

}
