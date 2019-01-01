package com.example.nogizaka46top.finial_test;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;


public class UpdateActivity extends AppCompatActivity {
    private TextView txtNoU, txtnameU, txtTelU;
    private RadioButton radBoyU, radGirU;
    private RadioGroup genderGroupU;
    private Button btnUpdate, btnPre, btnDelete, btnBac;
    private Spinner sprAgeU;
    long _id;
    private Cursor cursor;
    String[] age = new String[]{"10", "20", "30"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        txtNoU = findViewById(R.id.txtNoU);
        txtnameU = findViewById(R.id.txtnameU);
        txtTelU = findViewById(R.id.txtTelU);
        radBoyU = findViewById(R.id.radBoyU);
        radGirU = findViewById(R.id.radGirU);
        genderGroupU = findViewById(R.id.genderGroupU);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnPre = findViewById(R.id.btnPre);
        btnBac = findViewById(R.id.btnBac);
        sprAgeU = findViewById(R.id.sprAgeU);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(UpdateActivity.this, android.R.layout.simple_spinner_item, age);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sprAgeU.setAdapter(adapter);



        Intent it = getIntent();
        _id = it.getLongExtra("pkey", -1);

        MyDBHelper dbHelper = new MyDBHelper(UpdateActivity.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("select _id, name, age, gender, tel from person where _id="+ _id, null);

        if(cursor.moveToNext()) {
            loadData();
        }

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(UpdateActivity.this)
                        .setTitle("確認刪除")
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MyDBHelper mydb = new MyDBHelper(UpdateActivity.this);
                                SQLiteDatabase db = mydb.getWritableDatabase();
                                db.delete("person", "_id =" + _id, null);
                                setResult(RESULT_OK);
                                finish();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton btn = findViewById(genderGroupU.getCheckedRadioButtonId());
                MyDBHelper mydb = new MyDBHelper(UpdateActivity.this);
                SQLiteDatabase db = mydb.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("name", txtnameU.getText().toString());
                cv.put("gender", btn.getText().toString());
                cv.put("age", sprAgeU.getSelectedItem().toString());
                cv.put("tel", txtTelU.getText().toString());
                db.update("person", cv, "_id =" + _id, null);
                db.close();
                setResult(RESULT_OK);
                finish();
            }
        });

        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDBHelper dbHelper = new MyDBHelper(UpdateActivity.this);
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                Cursor nextC = db.rawQuery("select * from person where _id >" + _id, null);
                if (nextC.moveToNext()) {
                    txtNoU.setText(nextC.getString(nextC.getColumnIndex("_id")));
                    txtnameU.setText(nextC.getString(nextC.getColumnIndex("name")));

                    if (nextC.getString(nextC.getColumnIndex("gender")).equals("男")) {
                        radBoyU.setChecked(true);
                        Log.d("gender", "onCreate: boy");
                    } else if (nextC.getString(nextC.getColumnIndex("gender")).equals("女")) {
                        radGirU.setChecked(true);
                    }

                    if (nextC.getString(nextC.getColumnIndex("age")).equals("10")) {
                        sprAgeU.setSelection(0);
                    } else if (nextC.getString(nextC.getColumnIndex("age")).equals("20")) {
                        sprAgeU.setSelection(1);
                    } else if (nextC.getString(nextC.getColumnIndex("age")).equals("30")) {
                        sprAgeU.setSelection(2);
                    }

                    txtTelU.setText(nextC.getString(nextC.getColumnIndex("tel")));

                    _id += 1;
                }
            }
        });

        btnBac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDBHelper dbHelper = new MyDBHelper(UpdateActivity.this);
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                Cursor nextC = db.rawQuery("select * from person where _id < "+ _id + " order by _id desc" , null);
                if (nextC.moveToNext()) {
                    txtNoU.setText(nextC.getString(nextC.getColumnIndex("_id")));
                    txtnameU.setText(nextC.getString(nextC.getColumnIndex("name")));

                    if (nextC.getString(nextC.getColumnIndex("gender")).equals("男")) {
                        radBoyU.setChecked(true);
                        Log.d("gender", "onCreate: boy");
                    } else if (nextC.getString(nextC.getColumnIndex("gender")).equals("女")) {
                        radGirU.setChecked(true);
                    }

                    if (nextC.getString(nextC.getColumnIndex("age")).equals("10")) {
                        sprAgeU.setSelection(0);
                    } else if (nextC.getString(nextC.getColumnIndex("age")).equals("20")) {
                        sprAgeU.setSelection(1);
                    } else if (nextC.getString(nextC.getColumnIndex("age")).equals("30")) {
                        sprAgeU.setSelection(2);
                    }

                    txtTelU.setText(nextC.getString(nextC.getColumnIndex("tel")));

                    _id --;
                }
            }
        });


    }

    private void loadData() {
        txtNoU.setText(cursor.getString(cursor.getColumnIndex("_id")));
        txtnameU.setText(cursor.getString(cursor.getColumnIndex("name")));

        if(cursor.getString(cursor.getColumnIndex("gender")).equals("男")){
            radBoyU.setChecked(true);
            Log.d("gender", "onCreate: boy");
        }else if(cursor.getString(cursor.getColumnIndex("gender")).equals("女")){
            radGirU.setChecked(true);
        }

        if(cursor.getString(cursor.getColumnIndex("age")).equals("10")){
            sprAgeU.setSelection(0);
        }else if(cursor.getString(cursor.getColumnIndex("age")).equals("20")) {
            sprAgeU.setSelection(1);
        } else if(cursor.getString(cursor.getColumnIndex("age")).equals("30")) {
            sprAgeU.setSelection(2);
        }

        txtTelU.setText(cursor.getString(cursor.getColumnIndex("tel")));
    };

}
