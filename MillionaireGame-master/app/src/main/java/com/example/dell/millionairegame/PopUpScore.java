package com.example.dell.millionairegame;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PopUpScore extends AppCompatActivity {

    private Button continuePlaying , takeMoney ;
    MediaPlayer correctAnswer;
    private TextView scoreMoney;
    MediaPlayer winner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreenApp();
        setContentView(R.layout.activity_pop_up_score);

        setWidthAndHeight();

        continuePlaying = findViewById(R.id.continue_playing);
        takeMoney = findViewById(R.id.take_money);
        scoreMoney = findViewById(R.id.scoreMoney);

        correctAnswer = new MediaPlayer();
        winner = new MediaPlayer();

        correctAnswer = MediaPlayer.create(PopUpScore.this , R.raw.correct_answer);
        winner = MediaPlayer.create(this , R.raw.win_one_mili);

        scoreMoney.setText(MainMenu.scoreMoney.poll());


        if (MainMenu.scoreMoney.size() == 0)
        {
            continuePlaying.setVisibility(View.INVISIBLE);
            winner.start();
        }
        else
        {
            correctAnswer.start();
        }


        continuePlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PopUpScore.this , MainActivity.class));
            }
        });

        takeMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }


    protected void onPause()
    {
        super.onPause();
        if (correctAnswer.isPlaying()) {
            correctAnswer.release();
        }
        if (winner.isPlaying()) {
            winner.release();
        }
    }

    public void setFullScreenApp()
    {
        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

            getWindow().getDecorView().setSystemUiVisibility(flags);
            final View decorView = getWindow().getDecorView();
            decorView
                    .setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener()
                    {
                        @Override
                        public void onSystemUiVisibilityChange(int visibility)
                        {
                            if((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0)
                            {
                                decorView.setSystemUiVisibility(flags);
                            }
                        }
                    });

    }

    public void  setWidthAndHeight()
    {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8) ,(int) (height*.5));
    }
}
