package com.example.dell.millionairegame;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.session.MediaSession;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

public class MainMenu extends AppCompatActivity {

    private Button newGame , quitGame;
    MediaPlayer game_start;

    public static ArrayList<Questions> easyQuestions , mediumQuestions , hardQuestions , veryHardQuestions;
    public static int numberOfQuestions;
    public static Queue<String> scoreMoney;
    public static ArrayList<Questions> gameQuestions;
    public static Questions currentQ;
    public static int usingFirstHelp , usingSecondHelp , usingThirdHelp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreenApp();
        setContentView(R.layout.activity_main_menu);

        game_start = new MediaPlayer();
        game_start = MediaPlayer.create(this , R.raw.game_start);
        game_start.start();

        newGame = findViewById(R.id.new_game);
        quitGame = findViewById(R.id.quit_game);

        easyQuestions = new ArrayList<Questions>();
        mediumQuestions = new ArrayList<Questions>();
        hardQuestions = new ArrayList<Questions>();
        veryHardQuestions = new ArrayList<Questions>();

        gameQuestions = new ArrayList<Questions>();
        currentQ = new Questions();

        scoreMoney = new LinkedList<String>();

        fillQuestionsFromFiles();
        numberOfQuestions = 0;
        fillScoreMoney();
        fillQuestionsOfCurrentGame();

        usingFirstHelp = 0;
        usingSecondHelp = 0;
        usingThirdHelp = 0;


        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game_start.stop();
                startActivity(new Intent(MainMenu.this , MainActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        quitGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this , QuitMessage.class));
            }
        });
    }

    @Override
    public void onBackPressed()
    {

    }

    protected void onPause()
    {
        super.onPause();
        if (game_start.isPlaying()) {
            game_start.pause();
        }
    }

    protected void onResume()
    {
        super.onResume();
        if (game_start != null)
        {
            game_start.start();
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


    public void fillQuestionsFromFiles()
    {
        fillArrayLists("easyQuestions.txt" , easyQuestions);
        fillArrayLists("mediumQuestions.txt" , mediumQuestions);
        fillArrayLists("hardQuestions.txt" , hardQuestions);
        fillArrayLists("veryHardQuestions.txt" , veryHardQuestions);
    }

    public void fillArrayLists(String fileName , ArrayList<Questions> arr)
    {
        Questions q ;
        try {
            InputStream inputStream = getAssets().open(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = reader.readLine()) != null)
            {
                q = new Questions();
                q.setQuestion(line);
                line = reader.readLine();
                q.setAnswerA(line);
                line = reader.readLine();
                q.setAnswerB(line);
                line = reader.readLine();
                q.setAnswerC(line);
                line = reader.readLine();
                q.setAnswerD(line);
                line = reader.readLine();
                q.setCorrectAnswer(Integer.parseInt(line));
                arr.add(q);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fillScoreMoney()
    {
        scoreMoney.add("100$");
        scoreMoney.add("200$");
        scoreMoney.add("300$");
        scoreMoney.add("500$");
        scoreMoney.add("1000$");
        scoreMoney.add("2000$");
        scoreMoney.add("4000$");
        scoreMoney.add("8000$");
        scoreMoney.add("16000$");
        scoreMoney.add("32000$");
        scoreMoney.add("64000$");
        scoreMoney.add("125000$");
        scoreMoney.add("250000$");
        scoreMoney.add("500000$");
        scoreMoney.add("1000000$");
    }

    public void fillQuestionsOfCurrentGame()
    {
        Set<Integer> set = new HashSet<Integer>();
        Random rand = new Random();
        while(set.size() != 4)
        {
            int number = rand.nextInt(20);
            set.add(number);
        }
        for (int x : set)
        {
            gameQuestions.add(easyQuestions.get(x));
        }

        set = new HashSet<Integer>();
        while(set.size() != 4)
        {
            int number = rand.nextInt(20);
            set.add(number);
        }
        for (int x : set)
        {
            gameQuestions.add(mediumQuestions.get(x));
        }

        set = new HashSet<Integer>();
        while(set.size() != 4)
        {
            int number = rand.nextInt(20);
            set.add(number);
        }
        for (int x : set)
        {
            gameQuestions.add(hardQuestions.get(x));
        }

        set = new HashSet<Integer>();
        while(set.size() != 3)
        {
            int number = rand.nextInt(12);
            set.add(number);
        }
        for (int x : set)
        {
            gameQuestions.add(veryHardQuestions.get(x));
        }

    }

    public static void currentGame()
    {
        currentQ = gameQuestions.get(0);
        gameQuestions.remove(0);
    }
}
