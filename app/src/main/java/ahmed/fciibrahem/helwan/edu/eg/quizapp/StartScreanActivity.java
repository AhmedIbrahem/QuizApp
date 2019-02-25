package ahmed.fciibrahem.helwan.edu.eg.quizapp;




import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class StartScreanActivity extends AppCompatActivity {
    TextView HighScoreTExtView;
    Spinner diffeclySpiner;
    Spinner CategorySpinner;
    private static final int REQUST_CODE_QUIZ=1;
    public static final String EXTRA_DIFFCALTY="extadifficalty";
    public static final String EXTRA_CategoryName="extracategoryname";
    public static final String EXTRA_CategoryId="extacategoryid";

    private static final String SHARD_PREFS="Shard_prefs";
    private static final String KEY_HIGHSCORE="KeyHighScore";
    private int HighScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screan);
        Button StartButton = findViewById(R.id.btn_strart_quiz);
        HighScoreTExtView = findViewById(R.id.textview_highscore);
        diffeclySpiner=findViewById(R.id.spiner_deficlty);
        CategorySpinner=findViewById(R.id.spiner_category);
        LoadCategory();
        LoadDefcaltyLevel();
        LoadHighScore();
        StartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              StratQuiz();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUST_CODE_QUIZ)
        {
            if(resultCode==RESULT_OK)
            {
                int Score=data.getIntExtra(QuizActivity.EXTRA_SCORE,0);
                if(Score>HighScore)
                {

                    UpdateHighScore(Score);
                }
            }

        }

    }
    private void StratQuiz()
    {
        Category selectedCategory=(Category)CategorySpinner.getSelectedItem();
        int CategoryId=selectedCategory.getId();
        String CategoryName=selectedCategory.getName();
        String Defficalty=diffeclySpiner.getSelectedItem().toString();
        Intent intent = new Intent(StartScreanActivity.this, QuizActivity.class);
        intent.putExtra(EXTRA_CategoryId,CategoryId);
        intent.putExtra(EXTRA_CategoryName,CategoryName);
        intent.putExtra(EXTRA_DIFFCALTY,Defficalty);
        startActivityForResult(intent,REQUST_CODE_QUIZ);
    }
    private void LoadCategory()
    {
        QuizDbHelper dbHelper=QuizDbHelper.getInstance(this);
        List<Category> categoryList=dbHelper.GetAllCategory();
        ArrayAdapter<Category> categoryAdpter=new ArrayAdapter<Category>(this,android.R.layout.simple_spinner_dropdown_item,categoryList);
        categoryAdpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CategorySpinner.setAdapter(categoryAdpter);
    }
    private void LoadDefcaltyLevel()
    {
        String[] deficltylevel=Question.GetAllDefucltyLevel();
        ArrayAdapter<String> deficaltyAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,deficltylevel);
        deficaltyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        diffeclySpiner.setAdapter(deficaltyAdapter);

    }
    private void LoadHighScore()
    {
        SharedPreferences sharedPreferences=getSharedPreferences(SHARD_PREFS,MODE_PRIVATE);
        HighScore=sharedPreferences.getInt(KEY_HIGHSCORE,0);
        HighScoreTExtView.setText("HighScore:"+HighScore);

    }

    private void UpdateHighScore(int score)
    {
        HighScore=score;
        HighScoreTExtView.setText("HighScore:"+HighScore);
        SharedPreferences preferences=getSharedPreferences(SHARD_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putInt(KEY_HIGHSCORE,HighScore);
        editor.apply();


    }


}
