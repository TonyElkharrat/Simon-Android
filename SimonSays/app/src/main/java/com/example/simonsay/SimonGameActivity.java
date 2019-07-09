package com.example.simonsay;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Vibrator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SimonGameActivity extends AppCompatActivity implements View.OnClickListener
{

    private TextView m_WatchPlayTV,m_numberRequestTV;
    private  RelativeLayout m_pannel;
    private int m_countOfTouch;
    private boolean v_gameover=false;
    private int m_SleepOfThread=0;
    private boolean v_doubleBackpresssed=false;
    private SharedPreferences m_sharedPreferences;
    private GameManager m_gameManager;
    private MediaPlayer m_mediaPlayer;
    private MusicButtonsTouch m_musicService;
    private boolean v_muteButton = false;
    private SharedPreferences.Editor m_Editor;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if( MusicOfGameButton.v_FirstPlayingMusic == false)
        {
            CheckSound();
        }
        else
        {
            MusicOfGameButton.m_mediaPlayer.pause();
        }

        CheckLayoutLevel(getIntent().getStringArrayListExtra("level").get(0));
        Initialize();
        m_gameManager.CreateLevel(getIntent().getStringArrayListExtra("level").get(0));
        m_SleepOfThread=Integer.parseInt(getIntent().getStringArrayListExtra("level").get(1));
        m_gameManager.TurnOfComputer(m_numberRequestTV,m_WatchPlayTV);
    }

    public void Initialize()
    {
        m_WatchPlayTV = findViewById(R.id.replace_repeat);
        m_numberRequestTV = findViewById(R.id.numberOfRequestTV);
        m_pannel = findViewById(R.id.mainRelativeLayout);
        m_sharedPreferences = getSharedPreferences("details",MODE_PRIVATE);
        m_Editor = m_sharedPreferences.edit();
        m_mediaPlayer = new MediaPlayer();
        m_musicService = new MusicButtonsTouch(SimonGameActivity.this);
        m_gameManager = new GameManager(m_pannel,m_musicService);
        SetonClickListener();
    }

    public void SetonClickListener()
    {
        for (View viewInTheLayout : m_gameManager.getListOfChildren())
        {
            if(viewInTheLayout instanceof ImageButton)
            {
                viewInTheLayout.setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId()== R.id.PauseButtonId)
        {
            PauseDialog();
        }

        else
        {
            m_musicService.PlayMusicOfTouch(v.getResources().getResourceName(v.getId()));

            if(!m_gameManager.CheckIdEqualToView(m_gameManager.getArrayOfColors().get(m_countOfTouch),v))
            {
                Vibrator vi = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vi.vibrate(1300);
                v_gameover=true;
                MakeGameOver();
            }

            m_countOfTouch++;

            if(m_countOfTouch  == m_gameManager.getNumberRequest()&&!v_gameover)
            {
                m_gameManager.TurnOfComputer(m_numberRequestTV,m_WatchPlayTV);
                m_Editor.putInt("record_of_the_user",m_gameManager.getNumberRequest()-1);
                m_Editor.commit();
                m_countOfTouch=0;
            }
        }

    }

    public void PauseDialog()
    {
        Dialog gameOverDialog = new Dialog(this);

        CreateCommonDialog(gameOverDialog,R.layout.pause_dialog);
       // View dialogview = CreateCommonDialog(R.layout.pause_dialog);
        final MusicOfGameButton musicGame = gameOverDialog.findViewById(R.id.MusciButtonId);
        musicGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                musicGame.ResumeOrPauseTheMusic();
            }
        });
    }

    public void MakeGameOver()
    {
        m_gameManager.AddScore(SimonGameActivity.this,new ContentValues(),getIntent().getStringArrayListExtra("level").get(0),m_gameManager.getNumberRequest());
        m_musicService.PlayMusic("Game Over");
        GameOverDialog();
    }

    public void CreateCommonDialog(Dialog i_Dialog,int i_Resources)
    {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        View dialogview = getLayoutInflater().inflate(i_Resources,null);
//        dialogview.setFocusableInTouchMode(true);
//        dialogview.requestFocus();
//        dialogview.setOnKeyListener(new View.OnKeyListener()
//        {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//
//                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
//
//                    Intent mainactivity = new Intent(SimonGameActivity.this, MainActivity.class);
//                    startActivity(mainactivity);
//                    return true;
//                }
//                return true;
//
//            }
//        });

//        builder.setView(dialogview);
//
//        AlertDialog  alertDialog = builder.create();
//        alertDialog.getWindow().getAttributes().windowAnimations = R.style.Slide;
//        alertDialog.show();
        i_Dialog.setContentView(i_Resources);
        i_Dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageButton homeButton = i_Dialog.findViewById(R.id.HomeButton);
        ImageButton restartGameButton = i_Dialog.findViewById(R.id.restartGame);
        homeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        restartGameButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                MusicOfGameButton.v_FirstPlayingMusic= false;
                Intent intent = getIntent();
                finish();
                startActivity(intent);

            }
        });
       i_Dialog.show();
    }

    public void GameOverDialog()
    {
        Dialog gameOverDialog = new Dialog(this);

//        View dialogview = CreateCommonDialog(R.layout.game_over_dialog);
//
        CreateCommonDialog(gameOverDialog,R.layout.game_over_dialog);
        ImageButton saveButton = gameOverDialog.findViewById(R.id.SaveButton);
        final EditText nameOfTheUser = gameOverDialog.findViewById(R.id.nickname);
        final TextView recordOfTheUserTv = gameOverDialog.findViewById(R.id.recordTextOfTheUser);

        //ImageView trophyicone = dialogview.findViewById(R.id.TrophyId);
        ImageView ShareButton = gameOverDialog.findViewById(R.id.SaveButton);
        ImageView gameOver = gameOverDialog.findViewById(R.id.imageOfSimon);
        Animation animZomm= AnimationUtils.loadAnimation(this, R.anim.zoomout1);
        gameOver.startAnimation(animZomm);

        saveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                m_Editor.putString("user_name",nameOfTheUser.getText().toString());
                m_Editor.putInt("record_of_the_user",m_gameManager.getNumberRequest()-1);
                m_Editor.commit();
                int temp = m_sharedPreferences.getInt("record_of_the_user",0);
            }
        });

        ShareButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT,"New Score"+m_gameManager.getNumberRequest()+" !! Come to break it !");
                shareIntent.putExtra(Intent.EXTRA_EMAIL,new String[2]);
                shareIntent.setType("text/html");
                startActivity(Intent.createChooser(shareIntent,"Send Record With?"));
            }
        });

        CheckIfFirstTimeEnter(saveButton,nameOfTheUser);
        CheckIfBestScore(recordOfTheUserTv);

    }

    public void CheckIfFirstTimeEnter(ImageButton i_saveButton, EditText i_nameOfTheUser)
    {
        boolean isFirstTimeEnter= m_sharedPreferences.getBoolean("first_time_in_app",false);
        if(isFirstTimeEnter)
        {
            i_saveButton.setVisibility(View.VISIBLE);
            i_nameOfTheUser.setVisibility(View.VISIBLE);
        }
        else
        {
            i_saveButton.setVisibility(View.INVISIBLE);
            i_nameOfTheUser.setVisibility(View.INVISIBLE);
        }
    }

    public void CheckIfBestScore(TextView i_recordOfTheUserTv)
    {
        if (m_sharedPreferences.getInt("record_of_the_user",0)< m_gameManager.getNumberRequest()-1)
        {
            i_recordOfTheUserTv.setText("NEW RECORD "+(m_gameManager.getNumberRequest()-1));
            m_Editor.putInt("record_of_the_user",m_gameManager.getNumberRequest()-1);
            m_Editor.commit();

        }
        else
        {
            i_recordOfTheUserTv.setText("Your score is :"+(m_gameManager.getNumberRequest()-1));

        }
    }

    public void CheckSound()
    {
        if(MusicOfGameButton.v_IsPlaying)
        {
            MusicOfGameButton.m_mediaPlayer.start();
        }
    }

    public void CheckLayoutLevel(String i_Level)
    {
        if(i_Level.equalsIgnoreCase("Easy"))
        {
            setContentView(R.layout.easy_level);
        }

        else if(i_Level.equalsIgnoreCase("Medium"))
        {
            setContentView(R.layout.medium_level);
        }

        else
        {
            setContentView(R.layout.hard_level);
        }
    }

    @Override
    public void onBackPressed()
    {
        if(v_doubleBackpresssed)
        {
            CheckSound();
            finish();
        }

        else
        {
            v_doubleBackpresssed=true;
            Toast.makeText(SimonGameActivity.this,"Click again to exit",Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    v_doubleBackpresssed=false;
                }
            },2000);
        }   }
}






