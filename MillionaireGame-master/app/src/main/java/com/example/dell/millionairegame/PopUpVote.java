package com.example.dell.millionairegame;

import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class PopUpVote extends AppCompatActivity {

    private TextView voteA , voteB , voteC , voteD;
    MediaPlayer votingTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreenApp();
        setContentView(R.layout.activity_pop_up_vote);

        votingTrack = new MediaPlayer();
        votingTrack = MediaPlayer.create(this , R.raw.voting);
        votingTrack.start();

        voteA =  findViewById(R.id.voteA);
        voteB =  findViewById(R.id.voteB);
        voteC =  findViewById(R.id.voteC);
        voteD =  findViewById(R.id.voteD);

        setWidthAndHeight();

        int[] arr = new int[4];

        fillVotes(arr);


    }

    protected void onPause()
    {
        super.onPause();
        if (votingTrack.isPlaying()) {
            votingTrack.release();
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

    public void fillVotes(int[] arr)
    {
        int correctA = MainMenu.currentQ.getCorrectAnswer();

        Random percentage = new Random();
        arr[0]= percentage.nextInt(15);
        arr[1]= percentage.nextInt(25);
        arr[2]= percentage.nextInt(20);
        arr[3]= 100-(arr[0]+arr[1]+arr[2]); // correct answer

        if (correctA == 1)
        {
            int temp = arr[3];
            arr[3] = arr[0];
            arr[0] = temp;
        }
        else if (correctA == 2)
        {
            int temp = arr[3];
            arr[3] = arr[1];
            arr[1] = temp;
        }
        else if (correctA == 3)
        {
            int temp = arr[3];
            arr[3] = arr[2];
            arr[2] = temp;
        }

        voteA.setText("A --> " + arr[0] + '%');
        voteB.setText("B --> " + arr[1] + '%');
        voteC.setText("C --> " + arr[2] + '%');
        voteD.setText("D --> " + arr[3] + '%');

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
