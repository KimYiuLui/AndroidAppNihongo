package com.kimyiului.nihongo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class LectureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture);

        Toolbar lectureToolBar = findViewById(R.id.lecturetoolbar);
        setSupportActionBar(lectureToolBar);
        getSupportActionBar().setTitle("Lectures");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
