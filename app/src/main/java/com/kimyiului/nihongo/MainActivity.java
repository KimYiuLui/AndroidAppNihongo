package com.kimyiului.nihongo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;


public class MainActivity extends AppCompatActivity {
    private RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = findViewById(R.id.mainlayout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStartMenu();
            }
        });
        DbHelper dbHelper = new DbHelper(this);
        dbHelper.CheckUserTable();
    }
    public void openStartMenu(){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}


