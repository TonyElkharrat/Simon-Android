package com.example.simonsay;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.os.Vibrator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SimonGameActivity extends AppCompatActivity implements View.OnClickListener
{

    private TextView m_numbeOfRequestTv;
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


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CheckSound();
        String level = getIntent().getStringArrayListExtra("level").get(0);
        if(level.equalsIgnoreCase("Easy"))
        {
            setContentView(R.layout.easy_level);

        }
        else if(level.equalsIgnoreCase("Medium"))
        {
            setContentView(R.layout.medium_level);

        }
        else
        {
            setContentView(R.layout.hard_level);

        }
        Initialize();
        m_gameManager.CreateLevel(level);
        m_SleepOfThread=Integer.parseInt(getIntent().getStringArrayListExtra("level").get(1));
        m_gameManager.TurnOfComputer(m_numbeOfRequestTv);
    }

    public void Initialize()
    {
        m_numbeOfRequestTv = findViewById(R.id.numberOfRequestTV);
        m_pannel = findViewById(R.id.mainRelativeLayout);
        ImageButton restartgame = findViewById(R.id.restartGame);
        m_sharedPreferences = getSharedPreferences("details",MODE_PRIVATE);
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
                m_countOfTouch=0;
                m_gameManager.TurnOfComputer(m_numbeOfRequestTv);
            }
        }

    }

    public void PauseDialog()
    {
        View dialogview = CreateDialog(R.layout.pause_dialog);
        final MusicOfGameButton musicGame = dialogview.findViewById(R.id.MusciButtonId);
        final ImageButton restartGameButton = dialogview.findViewById(R.id.restartGame);
        restartGameButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);

            }
        });
        final ImageButton homeButton = dialogview.findViewById(R.id.HomeButton);
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

    public View CreateDialog(int i_Resources)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogview = getLayoutInflater().inflate(i_Resources,null);
        dialogview.setFocusableInTouchMode(true);
        dialogview.requestFocus();
        dialogview.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    Intent mainactivity = new Intent(SimonGameActivity.this, MainActivity.class);
                    startActivity(mainactivity);
                    return true;
                }
                return true;

            }
        });

        builder.setView(dialogview);
        AlertDialog  alertDialog = builder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.Slide;
        alertDialog.show();
        return dialogview;
    }

    public void GameOverDialog()
    {
        View dialogview = CreateDialog(R.layout.game_over_dialog);

        ImageButton saveButton = dialogview.findViewById(R.id.SaveButton);
        final EditText nameOfTheUser = dialogview.findViewById(R.id.nickname);
        final TextView recordOfTheUserTv = dialogview.findViewById(R.id.recordTextOfTheUser);
        final SharedPreferences.Editor editor = m_sharedPreferences.edit();

        ImageButton restartGame = dialogview.findViewById(R.id.restartGame);
        ImageButton homeButton = dialogview.findViewById(R.id.HomeButton);
        //ImageView trophyicone = dialogview.findViewById(R.id.TrophyId);
        ImageView ShareButton = dialogview.findViewById(R.id.SaveButton);


        saveButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {

                editor.putString("user_name",nameOfTheUser.getText().toString());
                editor.putInt("record_of_the_user",m_gameManager.getNumberRequest()-1);

                editor.commit();
                int temp = m_sharedPreferences.getInt("record_of_the_user",0);

            }
        });



        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

        CheckIfBestScore(recordOfTheUserTv,editor,saveButton,nameOfTheUser);

    }

    public void CheckIfBestScore(TextView i_recordOfTheUserTv,SharedPreferences.Editor i_editor,ImageButton i_saveButton, EditText i_nameOfTheUser)
    {
        if (m_sharedPreferences.getInt("record_of_the_user",0)< m_gameManager.getNumberRequest()-1)
        {
            i_recordOfTheUserTv.setText("NEW RECORD "+(m_gameManager.getNumberRequest()-1));
            i_editor.putInt("record_of_the_user",m_gameManager.getNumberRequest()-1);
            i_editor.commit();
            i_saveButton.setVisibility(View.VISIBLE);
            i_nameOfTheUser.setVisibility(View.VISIBLE);
        }

        else
        {
            i_recordOfTheUserTv.setText("Your score is :"+(m_gameManager.getNumberRequest()-1));
            i_saveButton.setVisibility(View.INVISIBLE);
            i_nameOfTheUser.setVisibility(View.INVISIBLE);
        }
    }

    public void CheckSound()
    {
        if(MusicOfGameButton.v_IsPlaying)
        {
            MusicOfGameButton.m_mediaPlayer.start();
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
        }    }
}






