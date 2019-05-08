package com.kimyiului.nihongo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

import com.kimyiului.nihongo.QuizModel.*;
import com.kimyiului.nihongo.UserModel.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class DbHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "ShinNihongo.db";


    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_QUIZ_TABLE = "CREATE TABLE " + QuizTable.TABLE_NAME + "( " + QuizTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + QuizTable.question + " TEXT, " + QuizTable.optionOne + " TEXT, " + QuizTable.optionTwo + " TEXT, " + QuizTable.optionThree + " TEXT, " + QuizTable.optionFour + " TEXT, " + QuizTable.answer + " TEXT" + "  )";
        db.execSQL(CREATE_QUIZ_TABLE);
    }

    //-------------------------------------------------------------------------------
    // Future edit this class in order to get make custom lesson quizzes
    public void FillQuizTable() {
        String[] kanaArr = new String[]{"a", "i", "u", "e", "o", "ka", "ki", "ku", "ke", "ko", "sa", "shi", "su", "se", "so", "ta", "chi", "tsu", "te", "to", "na", "ni", "nu", "ne", "no", "ha", "hi", "fu", "he", "ho", "ma", "mi", "mu", "me", "mo", "ya", "yu", "yo", "ra", "ri", "ru", "re", "ro", "wa", "wo", "n"};

        for (int i = 0; i < 200; i++) {
            Quiz quiz = GeneerateQuizQeustion(kanaArr);
            AddQuizQuestion(quiz);
        }
    }

    //--------------------------------------------------------------------------------

    public Quiz GeneerateQuizQeustion(String[] kanaArr) {
        String finalanswer = null;
        ArrayList<Integer> randomList = new ArrayList<>();
        String[] options = new String[4];
        Random random = new Random();

        //create array between 0 -45 cuz katakana and hiragana have 46 characters
        for (int i = 0; i < kanaArr.length; i++) {
            randomList.add(new Integer(i));
        }

        //shuffle tha array order to get random first 4 index
        Collections.shuffle(randomList);
        for (int i = 0; i < 4; i++) {
            options[i] = kanaArr[randomList.get(i)];
        }

        //randomly select a question out of the option and assign it to answer and question
        finalanswer = options[random.nextInt(4)];
        String opt1 = options[0];
        String opt2 = options[1];
        String opt3 = options[2];
        String opt4 = options[3];
        Quiz quiz = new Quiz(finalanswer, opt1, opt2, opt3, opt4, finalanswer);
        return quiz;
    }

    public void AddQuizQuestion(Quiz quiz) {
        ContentValues cv = new ContentValues();
        cv.put(QuizTable.question, quiz.getQuestion());
        cv.put(QuizTable.optionOne, quiz.getOptionOne());
        cv.put(QuizTable.optionTwo, quiz.getOptionTwo());
        cv.put(QuizTable.optionThree, quiz.getOptionThree());
        cv.put(QuizTable.optionFour, quiz.getOptionFour());
        cv.put(QuizTable.answer, quiz.getAnswer());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(QuizTable.TABLE_NAME, null, cv);
    }

    public ArrayList GetAllQuizQuestion() {
        ArrayList questionList = new ArrayList<>();
        String query = "SELECT * FROM " + QuizTable.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Quiz quiz = new Quiz(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
                questionList.add(quiz);
            }
            while (cursor.moveToNext());
        }
        db.close();
        return questionList;
    }

    public void InsertScore(){
        ContentValues cv = new ContentValues();
        cv.put(UserTable.score, "Score: 0");
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("user", null, cv);
    }

    //need to rewrite it better # 1 AM coding
    public User GetUserScore(){
        String query = "SELECT * FROM " + UserTable.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null );
        if(cursor != null){
            cursor.moveToFirst();
            User getUser = new User(cursor.getString(1));
            db.close();
            return getUser;
        }
        User getUser = new User("Score: 0");
        db.close();
        return getUser;

    }

    public void UpdateUserScore(String updateValue){
        ContentValues cv = new ContentValues();
        cv.put(UserTable.score, updateValue);

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(UserTable.TABLE_NAME, cv, UserTable._ID + " = 1", null);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuizTable.TABLE_NAME);
        onCreate(db);
    }

//check if usertable exists if so let it be
    public void CheckUserTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + UserTable.TABLE_NAME, null );
            if (cursor == null){
                String CREATE_USER_TABLE = "CREATE TABLE " + UserTable.TABLE_NAME + " ( " + UserTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , score TEXT )";
                db.execSQL(CREATE_USER_TABLE);
                InsertScore();
                db.close();
            }
        }catch (Exception e){
            String CREATE_USER_TABLE = "CREATE TABLE " + UserTable.TABLE_NAME + " ( " + UserTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , score TEXT )";
            db.execSQL(CREATE_USER_TABLE);
            InsertScore();
            db.close();
        }

    }

    //every time when user go to main menu the quiz will be dropped and a new table will be generated
    //In that case user will won't encounter same questions every time
    public void RecreateQuizTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + QuizTable.TABLE_NAME);
        onCreate(db);
        FillQuizTable();
        db.close();
    }
}
