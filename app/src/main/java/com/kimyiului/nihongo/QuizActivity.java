package com.kimyiului.nihongo;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView questionImage;
    private TextView correctScore;
    private TextView questionNum;
    private Button aBtn;
    private Button bBtn;
    private Button cBtn;
    private Button dBtn;
    private Button nextBtn;
    private Button menuBtn;

    private List<Quiz> quizList;
    private int questionCurrent;
    private int questiontotal;
    private int score;
    private Quiz currentQuiz;
    private Drawable defaultBackground;
    private int IMG_ID;
    private boolean answered;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionImage = findViewById(R.id.QuestionImg);
        questionNum = findViewById(R.id.questionNum);
        correctScore = findViewById(R.id.textViewQuizCorrect);
        aBtn = findViewById(R.id.QuizAbtn);
        aBtn.setOnClickListener(this);
        bBtn = findViewById(R.id.QuizBbtn);
        bBtn.setOnClickListener(this);
        cBtn = findViewById(R.id.QuizCbtn);
        cBtn.setOnClickListener(this);
        dBtn = findViewById(R.id.QuizDbtn);
        dBtn.setOnClickListener(this);
        menuBtn=findViewById(R.id.Exitbutton);
        nextBtn = findViewById(R.id.quizNextbtn);
        defaultBackground = aBtn.getBackground();

        DbHelper dbHelper = new DbHelper(this);
        quizList = dbHelper.GetAllQuizQuestion();
        questiontotal = quizList.size();
        Collections.shuffle(quizList);

        DisplayNextQuestion();
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuizExitDialog();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered) {
                    Toast.makeText(getApplicationContext(), "Please answer this question first. ^^", Toast.LENGTH_LONG).show();
                } else {
                    DisplayNextQuestion();
                }
            }
        });

    }

    @Override
    public void onBackPressed(){
    }

    private void DisplayNextQuestion() {
        aBtn.setEnabled(true);
        bBtn.setEnabled(true);
        cBtn.setEnabled(true);
        dBtn.setEnabled(true);
        aBtn.setTextColor(Color.BLACK);
        bBtn.setTextColor(Color.BLACK);
        cBtn.setTextColor(Color.BLACK);
        dBtn.setTextColor(Color.BLACK);
        aBtn.setBackgroundColor(Color.LTGRAY);
        bBtn.setBackgroundColor(Color.LTGRAY);
        cBtn.setBackgroundColor(Color.LTGRAY);
        dBtn.setBackgroundColor(Color.LTGRAY);

        if (questionCurrent < questiontotal) {
            answered = false;
            currentQuiz = quizList.get(questionCurrent);
            IMG_ID = getResources().getIdentifier(currentQuiz.getAnswer(), "drawable", getPackageName());
            questionImage.setImageResource(IMG_ID);
            aBtn.setText(currentQuiz.getOptionOne());
            bBtn.setText(currentQuiz.getOptionTwo());
            cBtn.setText(currentQuiz.getOptionThree());
            dBtn.setText(currentQuiz.getOptionFour());
            questionNum.setText("Q. " + (questionCurrent + 1));

            correctScore.setText("Correct:" + score + " / " + questiontotal);
            questionCurrent++;
        } else {
            finishedQuiz();
        }

    }

    private void checkAnswer(String givenAnswer) {
        aBtn.setEnabled(false);
        bBtn.setEnabled(false);
        cBtn.setEnabled(false);
        dBtn.setEnabled(false);
        answered = true;
        if(currentQuiz.getAnswer().equals(givenAnswer)) {
            score++;
            correctScore.setText("Correct:" + score + " / " + questiontotal);
        }
        ShowAnswer();

    }

    private void ShowAnswer() {
        aBtn.setTextColor(Color.WHITE);
        bBtn.setTextColor(Color.WHITE);
        cBtn.setTextColor(Color.WHITE);
        dBtn.setTextColor(Color.WHITE);
        aBtn.setBackgroundColor(Color.parseColor("#650000"));
        bBtn.setBackgroundColor(Color.parseColor("#650000"));
        cBtn.setBackgroundColor(Color.parseColor("#650000"));
        dBtn.setBackgroundColor(Color.parseColor("#650000"));

        if (currentQuiz.getAnswer().equals(currentQuiz.getOptionOne())) {
            aBtn.setTextColor(Color.BLACK);
            aBtn.setBackgroundColor(Color.LTGRAY);

        }
        if (currentQuiz.getAnswer().equals(currentQuiz.getOptionTwo())) {
            bBtn.setTextColor(Color.BLACK);
            bBtn.setBackgroundColor(Color.LTGRAY);
        }
        if (currentQuiz.getAnswer().equals(currentQuiz.getOptionThree())) {
            cBtn.setTextColor(Color.BLACK);
            cBtn.setBackgroundColor(Color.LTGRAY);
        }
        if (currentQuiz.getAnswer().equals(currentQuiz.getOptionFour())) {
            dBtn.setTextColor(Color.BLACK);
            dBtn.setBackgroundColor(Color.LTGRAY);
        }

        if (questionCurrent >= questiontotal) {
            nextBtn.setText("Finish");
        }

    }

    private void finishedQuiz() {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.QuizAbtn:
                checkAnswer(currentQuiz.getOptionOne());

            case R.id.QuizBbtn:
                checkAnswer(currentQuiz.getOptionTwo());

            case R.id.QuizCbtn:
                checkAnswer(currentQuiz.getOptionThree());

            case R.id.QuizDbtn:
                checkAnswer(currentQuiz.getOptionFour());


        }
    }


    private void QuizExitDialog(){
        String UpdateScoreBeforeExit = "Correct: " + score + " / " + questiontotal;
        Dialog dialog = new Dialog(UpdateScoreBeforeExit, this);
        dialog.show(getSupportFragmentManager(), "Exit dialog");
    }

}
