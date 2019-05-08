package com.kimyiului.nihongo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {
    private Button lectureBtn, quizBtn, scoreBtn, settingBtn;
    private User getScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        lectureBtn = findViewById(R.id.lecturebutton);
        lectureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenLectureView();
            }
        });
        quizBtn = findViewById(R.id.quizbutton);
        quizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenQuizView();
            }
        });
        scoreBtn = findViewById(R.id.scorebutton);
        settingBtn = findViewById(R.id.settingbutton);
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenSettingView();
            }
        });

        DbHelper dbHelper = new DbHelper(this);
        dbHelper.RecreateQuizTable();
        getScore = dbHelper.GetUserScore();
        scoreBtn.setText(getScore.getScore());
    }
    public void OpenLectureView(){
        Intent intent = new Intent(this, LectureActivity.class);
        startActivity(intent);
    }

    public void OpenQuizView(){
        Intent intent = new Intent(this, QuizActivity.class);
        startActivity(intent);
    }

    public void OpenScoreView(){
        //when lectures are implemented then an actual score view will be made it is cool as it is for now
    }

    public void OpenSettingView(){
        Toast.makeText(getApplicationContext(), "Currently no setting yet", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onBackPressed(){
    }
}
