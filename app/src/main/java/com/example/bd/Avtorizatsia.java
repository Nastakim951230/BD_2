package com.example.bd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class Avtorizatsia extends AppCompatActivity implements View.OnClickListener {

    TextView usernameField, passwordField;
    Button loginBtn, signinBtn;

    DBHelper dbHelper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avtorizatsia);
        usernameField=findViewById(R.id.Username);
        passwordField=findViewById(R.id.Password);

        loginBtn=findViewById(R.id.Vhod);
        loginBtn.setOnClickListener(this);
        signinBtn=findViewById(R.id.registr);
        signinBtn.setOnClickListener(this);

        dbHelper= new DBHelper(this);
        database=dbHelper.getWritableDatabase();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.Vhod:
                Cursor logcursor=database.query(DBHelper.TABLE_USERS, null, null, null, null, null, null);

                boolean loqqed=false;
                if (logcursor.moveToFirst()){
                    int usernameIndex=logcursor.getColumnIndex(DBHelper.KEY_USER);
                    int passwordIndex=logcursor.getColumnIndex(DBHelper.KEY_PASSWORD);
                    do{
                        if(usernameField.getText().toString().equals(logcursor.getString(usernameIndex))&& passwordField.getText().toString().equals(logcursor.getString(passwordIndex))){
                            startActivity(new Intent(this,MainActivity.class));
                           loqqed=true;
                           break;
                        }
                    }while (logcursor.moveToNext());
                }
                logcursor.close();
                if(!loqqed) Toast.makeText(this,"?????????????????? ???????????????????? ???????????? ?? ???????????? ???? ???????? ??????????????.",Toast.LENGTH_LONG).show();

                break;
            case R.id.registr:
                Cursor Signcursor=database.query(DBHelper.TABLE_USERS, null, null, null, null, null, null);

                boolean finded=false;
                if(Signcursor.moveToFirst()){
                    int usernameIndex=Signcursor.getColumnIndex(DBHelper.KEY_USER);
                    do{
                        if (usernameField.getText().toString().equals(Signcursor.getString(usernameIndex))){
                            Toast.makeText(this,"?????????????????? ???????? ?????????? ?????? ??????????????????????????????.",Toast.LENGTH_LONG).show();
                            finded=true;
                            break;
                        }
                    }while (Signcursor.moveToNext());
                }
                if(!finded){
                    ContentValues  contentValues= new ContentValues();
                    contentValues.put(DBHelper.KEY_USER,usernameField.getText().toString());
                    contentValues.put(DBHelper.KEY_PASSWORD,passwordField.getText().toString());
                    database.insert(DBHelper.TABLE_USERS,null,contentValues);
                    Toast.makeText(this,"???? ?????????????? ????????????????????????????????????",Toast.LENGTH_LONG).show();
                }
                Signcursor.close();
                break;
        }
    }
}