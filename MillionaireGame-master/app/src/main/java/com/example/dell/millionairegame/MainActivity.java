package com.example.dell.millionairegame;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private Button backToMainMenu , removeTwoChoices , audienceVote , friendHelp ,
    choiceA , choiceB , choiceC , choiceD;
    private TextView no_of_questions  , countDownTimer , question ;

    public MediaPlayer background_track ;
    public MediaPlayer danger_track;

    private LinearLayout answersLayout;
    private LinearLayout questionLayout;
    private LinearLayout helpersLayout;

    CountDownTimer cdt;

    public static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreenApp();
        setContentView(R.layout.activity_main);

        activity = this;

        backToMainMenu = findViewById(R.id.back_menu);
        removeTwoChoices = findViewById(R.id.remove2q);
        friendHelp = findViewById(R.id.friendhelp);
        audienceVote = findViewById(R.id.audienceHelp);
        choiceA = findViewById(R.id.choice_1);
        choiceB = findViewById(R.id.choice_2);
        choiceC = findViewById(R.id.choice_3);
        choiceD = findViewById(R.id.choice_4);

        questionLayout = findViewById(R.id.question_layout);
        helpersLayout = findViewById(R.id.helpers_layout);
        answersLayout = findViewById(R.id.answers_layout);

        no_of_questions = findViewById(R.id.no_questions);
        countDownTimer = findViewById(R.id.timer);
        question = findViewById(R.id.question);

        back_track();
        timer();

        // Animation Part

        LayoutAnimationController leftLayoutAnimationController = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_slide_left);
        answersLayout.setLayoutAnimation(leftLayoutAnimationController);

        LayoutAnimationController fallDownAnimationController = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_fall_down);
        questionLayout.setLayoutAnimation(fallDownAnimationController);

        LayoutAnimationController rightLayoutAnimationController = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_slide_right);
        helpersLayout.setLayoutAnimation(rightLayoutAnimationController);

        MainMenu.currentGame();
        MainMenu.numberOfQuestions++;

        no_of_questions.setText(MainMenu.numberOfQuestions + "/15");

        question.setText(MainMenu.currentQ.getQuestion());

        ForegroundColorSpan orangeChoice  = new ForegroundColorSpan(getResources().getColor(R.color.orange));

        SpannableString A = new SpannableString(MainMenu.currentQ.getAnswerA());
        SpannableString B = new SpannableString(MainMenu.currentQ.getAnswerB());
        SpannableString C = new SpannableString(MainMenu.currentQ.getAnswerC());
        SpannableString D = new SpannableString(MainMenu.currentQ.getAnswerD());

        A.setSpan(orangeChoice , 0 , 2 , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        B.setSpan(orangeChoice , 0 , 2 , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        C.setSpan(orangeChoice , 0 , 2 , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        D.setSpan(orangeChoice , 0 , 2 , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        choiceA.setText(A);
        choiceB.setText(B);
        choiceC.setText(C);
        choiceD.setText(D);


        if (MainMenu.usingFirstHelp > 0)
        {
            removeTwoChoices.setVisibility(View.INVISIBLE);
        }
        if (MainMenu.usingSecondHelp > 0)
        {
            friendHelp.setVisibility(View.INVISIBLE);
        }
        if (MainMenu.usingThirdHelp > 0)
        {
            audienceVote.setVisibility(View.INVISIBLE);
        }

        backToMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cdt.cancel();
                Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

        removeTwoChoices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeTwoChoices.setVisibility(View.INVISIBLE);

                MainMenu.usingFirstHelp++;

                Random rand = new Random();
                int correct = MainMenu.currentQ.getCorrectAnswer();
                Set<Integer> set = new HashSet<Integer>();
                while (set.size() != 2)
                {
                    int number = 1 + rand.nextInt(4);
                    if (number != correct)
                        set.add(number);
                }

                for (int x : set)
                {
                    switch (x)
                    {
                        case 1:
                            choiceA.setVisibility(View.INVISIBLE);
                            break;
                        case 2:
                            choiceB.setVisibility(View.INVISIBLE);
                            break;
                        case 3:
                            choiceC.setVisibility(View.INVISIBLE);
                            break;
                        case 4:
                            choiceD.setVisibility(View.INVISIBLE);
                            break;
                    }
                }
            }


        });

        friendHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainMenu.usingSecondHelp++;
                friendHelp.setVisibility(View.INVISIBLE);
                startActivity(new Intent(MainActivity.this , PopUpAnswer.class));
            }
        });

        audienceVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainMenu.usingThirdHelp++;
                audienceVote.setVisibility(View.INVISIBLE);
                startActivity(new Intent(MainActivity.this , PopUpVote.class));
            }
        });



        choiceA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check(1);
            }
        });

        choiceB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 check(2);
            }
        });

        choiceC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check(3);
            }
        });

        choiceD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               check(4);
            }
        });


    }


    public void timer()
    {
        danger_track = new MediaPlayer();
        danger_track = MediaPlayer.create(this ,R.raw.count_down_end );
        cdt = new CountDownTimer(46000, 1000) {

            public void onTick(long millisUntilFinished) {
                countDownTimer.setText(millisUntilFinished / 1000 + "");
                if ((millisUntilFinished / 1000) == 5 ) {
                    countDownTimer.setBackgroundResource(R.drawable.dangerous_timer);
                    background_track.stop();
                    background_track = null;
                    danger_track.start();
                }
            }
            public void onFinish() {
                danger_track.stop();
                switch (MainMenu.currentQ.getCorrectAnswer())
                {
                    case 1:
                        choiceA.setBackgroundResource(R.drawable.correct_answer);
                        break;
                    case 2:
                        choiceB.setBackgroundResource(R.drawable.correct_answer);
                        break;
                    case 3:
                        choiceC.setBackgroundResource(R.drawable.correct_answer);
                        break;
                    case 4:
                        choiceD.setBackgroundResource(R.drawable.correct_answer);
                        break;
                }
                startActivity(new Intent(MainActivity.this , LoseTheGame.class));
            }
        };
        cdt.start();

    }

    @Override
    public void onBackPressed()
    {

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

    public void back_track()
    {
        background_track = new MediaPlayer();
        background_track = MediaPlayer.create(activity, R.raw.background_track);
        background_track.start();
    }

    protected void onPause() {
        super.onPause();
            if (background_track != null)
            {
                background_track.pause();
            }
            if (danger_track.isPlaying()) {
                danger_track.pause();
            }

    }

    protected void onResume()
    {
       super.onResume();
       if(background_track != null ) {
           background_track.start();
       }
       else{
           danger_track.start();
       }
   }

    public void check(int choice)
    {
      if (danger_track.isPlaying())
          danger_track.stop();
      cdt.cancel();

      int correct = MainMenu.currentQ.getCorrectAnswer();
      if (choice == correct)
      {
          correctAnswer(choice);
      }
      else
      {
          incorrectAnswer(choice , correct);
      }
  }

    public void correctAnswer(int choice)
    {
      switch (choice)
      {
          case 1:
              choiceA.setBackgroundResource(R.drawable.correct_answer);
              break;
          case 2:
              choiceB.setBackgroundResource(R.drawable.correct_answer);
              break;
          case 3:
              choiceC.setBackgroundResource(R.drawable.correct_answer);
              break;
          case 4:
              choiceD.setBackgroundResource(R.drawable.correct_answer);
              break;
      }
      startActivity(new Intent(MainActivity.this , PopUpScore.class));
  }

    public void incorrectAnswer(int choice , int correctA)
    {
      switch (choice)
      {
          case 1:
              choiceA.setBackgroundResource(R.drawable.incorrect_answer);
              break;
          case 2:
              choiceB.setBackgroundResource(R.drawable.incorrect_answer);
              break;
          case 3:
              choiceC.setBackgroundResource(R.drawable.incorrect_answer);
              break;
          case 4:
              choiceD.setBackgroundResource(R.drawable.incorrect_answer);
              break;
      }
      switch (correctA)
      {
          case 1:
              choiceA.setBackgroundResource(R.drawable.correct_answer);
              break;
          case 2:
              choiceB.setBackgroundResource(R.drawable.correct_answer);
              break;
          case 3:
              choiceC.setBackgroundResource(R.drawable.correct_answer);
              break;
          case 4:
              choiceD.setBackgroundResource(R.drawable.correct_answer);
              break;
      }
      startActivity(new Intent(MainActivity.this ,LoseTheGame.class ));
  }


}
