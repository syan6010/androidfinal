package com.example.nogizaka46top.finial_test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {
    public MyDBHelper(Context context) {
        super(context, "mydb.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreate = "create table if not exists person" + "(_id INTEGER PRIMARY KEY, name TEXT, gender TEXT, age TEXT, tel TEXT)";
        db.execSQL(sqlCreate);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
