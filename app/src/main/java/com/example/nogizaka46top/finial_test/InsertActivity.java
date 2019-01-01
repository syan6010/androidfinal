package com.example.nogizaka46top.finial_test;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class InsertActivity extends AppCompatActivity {
    private TextView txtNo, txtname, txtTel;
    private RadioButton radBoy, radGir;
    private RadioGroup genderGroup;
    private Button btnCan, btnSave;
    private Spinner sprAge;
    String[] age = new String[]{"10", "20", "30"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        txtNo = findViewById(R.id.txtNoU);
        txtname = findViewById(R.id.txtnameU);
        txtTel = findViewById(R.id.txtTelU);
        radBoy = findViewById(R.id.radBoy);
        radGir = findViewById(R.id.radGir);
        genderGroup = findViewById(R.id.genderGroupU);
        btnCan = findViewById(R.id.btnCan);
        btnSave = findViewById(R.id.btnSave);
        sprAge = findViewById(R.id.sprAgeU);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(InsertActivity.this, android.R.layout.simple_spinner_item, age);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sprAge.setAdapter(adapter);
        

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton btn = findViewById(genderGroup.getCheckedRadioButtonId());
                MyDBHelper mydb = new MyDBHelper(InsertActivity.this);
                ContentValues cv = new ContentValues();
                cv.put("name", txtname.getText().toString());
                cv.put("gender", btn.getText().toString());
                cv.put("age", sprAge.getSelectedItem().toString());
                cv.put("tel", txtTel.getText().toString());
                SQLiteDatabase db = mydb.getWritableDatabase();
                db.insert("person", null, cv);
                db.close();
                setResult(RESULT_OK);
                finish();
            }
        });


        btnCan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}
