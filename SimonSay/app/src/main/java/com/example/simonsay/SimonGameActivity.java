package com.example.simonsay;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
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
    private MusicService m_musicService;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


      String level = getIntent().getStringArrayListExtra("level").get(0);
        if(level.equalsIgnoreCase("Easy"))
        {
            setContentView(R.layout.game4);

        }
        else if(level.equalsIgnoreCase("Medium"))
        {
            setContentView(R.layout.game4);

        }
        else
        {
            setContentView(R.layout.game4);

        }
        Initialize();
        m_gameManager.CreateLevel();
        m_SleepOfThread=Integer.parseInt(getIntent().getStringArrayListExtra("level").get(1));
        m_gameManager.TurnOfComputer(m_numbeOfRequestTv);
    }

    public void Initialize()
    {
        m_numbeOfRequestTv = findViewById(R.id.numberOfRequestTV);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Pacifico.ttf");
        m_numbeOfRequestTv.setTypeface(typeface);
        m_pannel = findViewById(R.id.mainRelativeLayout);
        ImageButton restartgame = findViewById(R.id.restartGame);
        m_sharedPreferences = getSharedPreferences("details",MODE_PRIVATE);
        m_mediaPlayer = new MediaPlayer();
        m_musicService = new MusicService(SimonGameActivity.this);
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
        m_musicService.PlayMusicOfTouch(v.getResources().getResourceName(v.getId()));

        if(!m_gameManager.CheckIdEqualToView(m_gameManager.getArrayOfColors().get(m_countOfTouch),v))
        {
            v_gameover=true;
            GameOver();
        }

        m_countOfTouch++;

        if(m_countOfTouch  == m_gameManager.getNumberRequest()&&!v_gameover)
        {
            m_countOfTouch=0;
            m_gameManager.TurnOfComputer(m_numbeOfRequestTv);
        }
    }



    public void GameOver()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogview = getLayoutInflater().inflate(R.layout.game_over_dialog,null);
        dialogview.setFocusableInTouchMode(true);
        dialogview.requestFocus();
        dialogview.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    Intent tableScoreActivity = new Intent(SimonGameActivity.this, MainActivity.class);
                    startActivity(tableScoreActivity);
                    return true;
                }
                return true;

            }
            });

        builder.setView(dialogview);
        AlertDialog  alertDialog = builder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.Slide;
        alertDialog.show();

        Button saveButton = dialogview.findViewById(R.id.SaveButton);
        final EditText nameOfTheUser = dialogview.findViewById(R.id.inputOfTheUserName);
        final TextView recordOfTheUserTv = dialogview.findViewById(R.id.recordTextOfTheUser);
        final SharedPreferences.Editor editor = m_sharedPreferences.edit();
        ImageButton restartGame = dialogview.findViewById(R.id.restartGame);
        ImageButton homeButton = dialogview.findViewById(R.id.HomeButton);
        ImageView trophyicone = dialogview.findViewById(R.id.TrophyId);
        ImageView ShareButton = dialogview.findViewById(R.id.ShareGame);
        int temp =m_sharedPreferences.getInt("record_of_the_user",0);
//        gameManager.AddScore(new ContentValues(),eLevel.Level.Easy,Integer.parseInt(numbeOfRequestTv.getText().toString())-a);

        if (m_sharedPreferences.getInt("record_of_the_user",0)< m_gameManager.getNumberRequest()-1)
        {
            recordOfTheUserTv.setText(getString(R.string.HaveARecord)+(m_gameManager.getNumberRequest()-1));
            editor.putInt("record_of_the_user",m_gameManager.getNumberRequest()-1);
            editor.commit();
            saveButton.setVisibility(View.VISIBLE);
            nameOfTheUser.setVisibility(View.VISIBLE);
            trophyicone.setVisibility(View.VISIBLE);
        }

        else
        {
            recordOfTheUserTv.setText("Your score is :"+(m_gameManager.getNumberRequest()-1));
            saveButton.setVisibility(View.INVISIBLE);
            nameOfTheUser.setVisibility(View.INVISIBLE);
            trophyicone.setVisibility(View.INVISIBLE);
        }

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

        restartGame.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);

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
                shareIntent.putExtra(Intent.EXTRA_TEXT,getString(R.string.SendRecord)+m_gameManager.getNumberRequest()+" !! Come to break it !");
                shareIntent.putExtra(Intent.EXTRA_EMAIL,new String[2]);
                shareIntent.setType("text/html");
                startActivity(Intent.createChooser(shareIntent,getString(R.string.SendRecord)));
            }
        });

    }

    @Override
    public void onBackPressed()
    {
        if(v_doubleBackpresssed)
        {
            super.onBackPressed();
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
        }          }



}






