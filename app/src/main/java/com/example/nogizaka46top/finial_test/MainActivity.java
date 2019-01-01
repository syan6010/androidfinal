package com.example.nogizaka46top.finial_test;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {
    private Button btnInsert;
    private ListView lstData;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInsert = findViewById(R.id.btnInsert);
        lstData = findViewById(R.id.lstData);

        MyDBHelper dbHelper = new MyDBHelper(MainActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from person", null);
        adapter = new SimpleCursorAdapter(
                MainActivity.this,
                R.layout.mylistview,
                cursor,
                new String[]{"_id", "name", "age", "tel", "gender"},
                new int[]{R.id.lisNo, R.id.lisName, R.id.lisAge, R.id.lisTel, R.id.lisGen}, 0
        );
        lstData.setAdapter(adapter);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, InsertActivity.class);
                startActivityForResult(it, 99);
            }
        });


        lstData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(MainActivity.this, UpdateActivity.class);
                it.putExtra("pkey", id);
                startActivityForResult(it, 90);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 99){
            if(resultCode == RESULT_CANCELED) {
                Log.d("resultCode", "GOBACK");
            }

            if(resultCode == RESULT_OK) {
                MyDBHelper dbHelper = new MyDBHelper(MainActivity.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.rawQuery("select * from person", null);
                adapter.swapCursor(cursor);
                adapter.notifyDataSetChanged();
            }
        } else if(requestCode == 90){
            if(resultCode == RESULT_OK) {
                MyDBHelper dbHelper = new MyDBHelper(MainActivity.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.rawQuery("select * from person", null);
                adapter.swapCursor(cursor);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
