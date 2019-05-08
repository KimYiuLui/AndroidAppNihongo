package com.kimyiului.nihongo;

import android.annotation.SuppressLint;
import android.arch.core.util.Function;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;



public class Dialog extends AppCompatDialogFragment {
    private String Updatevalue;
    private Context context;
    Function stopTimer;
    Function startTimer;

    public Dialog(){}

    @SuppressLint("ValidFragment")
    public Dialog(String Updatevalue, Context context) {
        this.Updatevalue = Updatevalue;
        this.context = context;
    }

    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Back to Menu")
                .setMessage("Are you sure you want to leave?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BackToStartMenu(Updatevalue);
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        return builder.create();
    }

    public void BackToStartMenu(String updateScoreValue){
        DbHelper db = new DbHelper(context);
        db.UpdateUserScore(updateScoreValue);
        Intent intent = new Intent(getActivity(), MenuActivity.class);
        startActivity(intent);
    }
}
