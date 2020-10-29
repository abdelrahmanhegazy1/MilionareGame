package com.example.dell.millionairegame;

import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PopUpAnswer extends AppCompatActivity {

    TextView choice_answer;
    MediaPlayer friend_help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreenApp();
        setContentView(R.layout.activity_pop_up_answer);

        friend_help = new MediaPlayer();
        friend_help = MediaPlayer.create(this , R.raw.friend_help);
        friend_help.start();

        choice_answer = findViewById(R.id.answer);

        int correctChoice = MainMenu.currentQ.getCorrectAnswer();
        switch (correctChoice)
        {
            case 1:
               choice_answer.setText("maybe A");
                break;
            case 2:
                choice_answer.setText("maybe B");
                break;
            case 3:
                choice_answer.setText("maybe C");
                break;
            case 4:
                choice_answer.setText("maybe D");
                break;
        }



        setWidthAndHeight();
    }


    protected void onPause()
    {
        super.onPause();
        if (friend_help.isPlaying()) {
            friend_help.release();
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
        getWindow().setLayout((int)(width*.8) ,(int) (height*.4));
    }

}
