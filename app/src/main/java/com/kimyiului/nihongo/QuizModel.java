package com.kimyiului.nihongo;

import android.provider.BaseColumns;

public class QuizModel{
    private QuizModel(){}

    public static class QuizTable implements BaseColumns{
        public static final String TABLE_NAME = "quiz";
        public static final String question = "question";
        public static final String optionOne = "optionOne";
        public static final String optionTwo = "optionTwo";
        public static final String optionThree = "optionThree";
        public static final String optionFour = "optionFour";
        public static final String answer = "answer";
    }
}
