package ahmed.fciibrahem.helwan.edu.eg.quizapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity {
  TextView TextViewQuestion,TextViewScore,TextviewQuestionCount,TextViewCountDown,TEXTViewDeficlty,TextViewCategory;
    Button Confirm;
    RadioGroup radioGroupQuestions;
    RadioButton radioButton1,radioButton2,radioButton3;
    ArrayList<Question> questionList;
    private ColorStateList textcolordefoultrbg;
    private int questioncounter,questioncounttotal;
    private Question currentQuestion;
    boolean answerd;
    private int Score;
    public final static String EXTRA_SCORE="extraScore";
    private long backPrefrancetime;
    private static final long COUNTDOWN_IN_MILS=30000;
    private ColorStateList textcolordefoultcountdown;
    private long timelifetmilles;
    private CountDownTimer countDownTimer;
    public static final String KEY_SCORE=" keyscore";
    private static final String KEY_QUESTION_COUNT="keyquestioncount";
    private static final String KEY_MILLES="keymilles";
    private static final String Key_Answrd="answerd";
    private static final String KEY_QUESTIONlIST="keyquestionlist";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        TextViewQuestion=findViewById(R.id.textview_qution);
        TextViewScore=findViewById(R.id.textview_score);
        TextviewQuestionCount=findViewById(R.id.textview_questioncount);
        TextViewCountDown=findViewById(R.id.textview_countdown);
        radioGroupQuestions=findViewById(R.id.radio_group);
        radioButton1=findViewById(R.id.radio_btn_1);
        radioButton2=findViewById(R.id.radio_btn_2);
        radioButton3=findViewById(R.id.radio_btn_3);
        Confirm=findViewById(R.id.confirm);
        TEXTViewDeficlty=findViewById(R.id.textview_deficalty);
        TextViewCategory=findViewById(R.id.textview_category);

        textcolordefoultrbg=radioButton1.getTextColors();
        textcolordefoultcountdown=TextViewCountDown.getTextColors();
        Intent intent=getIntent();
        String deficalty= intent.getStringExtra(StartScreanActivity.EXTRA_DIFFCALTY);
        int CategoryId=intent.getIntExtra(StartScreanActivity.EXTRA_CategoryId,0);
        String CategoryName=intent.getStringExtra(StartScreanActivity.EXTRA_CategoryName);
        TEXTViewDeficlty.setText("Deffficlty :"+deficalty);
        TextViewCategory.setText("Category:"+CategoryName);


        if(savedInstanceState ==null) {
            QuizDbHelper quizDbHelper = QuizDbHelper.getInstance(this);
            questionList = quizDbHelper.GetSelectQuestionDefculty(CategoryId,deficalty);
            Log.i("DAta", "" + questionList.size());
            questioncounttotal = questionList.size();
            Collections.shuffle(questionList);
            ShowNextQuestion();
        }
        else
        {
            questionList=savedInstanceState.getParcelableArrayList(KEY_QUESTIONlIST);
            Log.i("oRetrivee", ""+questionList.size());

            questioncounttotal = questionList.size();
            questioncounter=savedInstanceState.getInt(KEY_QUESTION_COUNT);
            answerd=savedInstanceState.getBoolean(Key_Answrd);
            Score=savedInstanceState.getInt(KEY_SCORE);
            timelifetmilles=savedInstanceState.getLong(KEY_MILLES);
            currentQuestion=questionList.get(questioncounter-1);
            if(!answerd)
            {
                StrartCountDown();

            }
            else
            {
                UpdateCountDownText();
                showSloution();
            }


        }
       Confirm.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (!answerd) {
                   if (radioButton1.isChecked() || radioButton2.isChecked() || radioButton3.isChecked()) {
                       CheekAnswer();
                   } else {
                       Toast.makeText(QuizActivity.this, "Please Choose your answer", Toast.LENGTH_SHORT).show();
                   }
               } else {

                   ShowNextQuestion();
               }

           }

       });

    }
    private void ShowNextQuestion()
    {
        radioButton1.setTextColor(textcolordefoultrbg);
        radioButton2.setTextColor(textcolordefoultrbg);
        radioButton3.setTextColor(textcolordefoultrbg);
        radioGroupQuestions.clearCheck();
        if(questioncounter<questioncounttotal)
        {
            currentQuestion=questionList.get(questioncounter);
            TextViewQuestion.setText(currentQuestion.getQuestion());
            radioButton1.setText(currentQuestion.getOption1());
            radioButton2.setText(currentQuestion.getOption2());
            radioButton3.setText(currentQuestion.getOption3());
            questioncounter++;
            TextviewQuestionCount.setText("Question " +questioncounter +" /" +questioncounttotal);
            answerd=false;
            Confirm.setText("Confirm");


            timelifetmilles=COUNTDOWN_IN_MILS;
            StrartCountDown();
            }
            else
        {

            FinshQuiz();
        }




    }
    private   void StrartCountDown()
    {
        countDownTimer=new CountDownTimer(timelifetmilles,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timelifetmilles=millisUntilFinished;
                UpdateCountDownText();

            }

            @Override
            public void onFinish() {
                timelifetmilles=0;
                UpdateCountDownText();
                CheekAnswer();

            }
        }.start();

    }
    private void UpdateCountDownText()
    {
        int Mints= (int) (timelifetmilles/1000)/60;
        int Seconds=(int)(timelifetmilles/1000)% 60;
        String timeformated=String.format(Locale.getDefault(),"%02d:%02d",Mints,Seconds);
        TextViewCountDown.setText(timeformated);
        if(timelifetmilles<10000)
        {
            TextViewCountDown.setTextColor(Color.RED);

        }
        else
        {
            TextViewCountDown.setTextColor(textcolordefoultcountdown);
        }

    }

    private void CheekAnswer()

    {
        countDownTimer.cancel();
        answerd=true;
        RadioButton radioButtonselected= findViewById(radioGroupQuestions.getCheckedRadioButtonId());
        int cheekanswer =radioGroupQuestions.indexOfChild(radioButtonselected) +1;
        int CorrectAnswer = currentQuestion.getAnswernumber();
        if (cheekanswer == CorrectAnswer) {
            Score++;
            TextViewScore.setText("Score :"+  Score);
        }

        showSloution();
    }

    private void showSloution()
    {
        radioButton1.setTextColor(Color.RED);
        radioButton2.setTextColor(Color.RED);
        radioButton3.setTextColor(Color.RED);
        switch (currentQuestion.getAnswernumber())
        {
            case 1:
                radioButton1.setTextColor(Color.GREEN);
                TextViewQuestion.setText("Answer 1  is Corect");
                break;
            case 2:
                radioButton2.setTextColor(Color.GREEN);
                TextViewQuestion.setText("Answer 2  is Corect");
                break;
            case 3:
                radioButton3.setTextColor(Color.GREEN);
                TextViewQuestion.setText("Answer 3  is Corect");
                break;



        }
        if(questioncounter<questioncounttotal)
        {
            Confirm.setText("Next");
        }
        else
        {
            Confirm.setText("finsh");
        }


    }
    private   void FinshQuiz()
    {
        Intent resultintent=new Intent();
        resultintent.putExtra(EXTRA_SCORE,Score);
        setResult(RESULT_OK,resultintent);


        finish();

    }
    @Override
    public void onBackPressed() {
        if(backPrefrancetime+2000>System.currentTimeMillis())
        {
            FinshQuiz();
            
        }
        else 
        {
            Toast.makeText(this, "bress back agin to exit ", Toast.LENGTH_SHORT).show();
        }
        backPrefrancetime=System.currentTimeMillis();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(countDownTimer !=null)
        {
            countDownTimer.cancel();
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SCORE,Score);
        outState.putLong(KEY_MILLES,timelifetmilles);
        outState.putInt(KEY_QUESTION_COUNT,questioncounter);
        outState.putBoolean(Key_Answrd,answerd);
        outState.putParcelableArrayList(KEY_QUESTIONlIST,questionList);
        Log.i("onSaveInstanceState", ""+questionList.size());

    }
}
