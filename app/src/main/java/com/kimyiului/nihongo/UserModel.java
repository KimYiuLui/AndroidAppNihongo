package com.kimyiului.nihongo;

import android.provider.BaseColumns;

public class UserModel {
    private UserModel(){}

    public static class UserTable implements BaseColumns{
        public static final String TABLE_NAME = "user";

        public static final String score = "score";
    }

}
