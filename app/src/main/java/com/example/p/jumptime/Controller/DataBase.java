package com.example.p.jumptime.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class DataBase extends Fragment implements OnClickListener {

    static DBHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public void onClick(View view) {

    }

    public static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            // конструктор суперкласса
            super(context, "myDB", null, 9);// число отвечает за обновление

        }

        public void onCreate(SQLiteDatabase db) {
            Log.d("LOG", "--- onCreate database ---");
            //Создание таблиц
            db.execSQL("create table mytable ("
                    + "id integer primary key autoincrement,"
                    + "name text,"
                    + "data text,"
                    + "time text,"
                    + "k text,"
                    + "i text,"
                    + "l text,"
                    + "o text,"
                    + "priority  text,"

                    + "project text,"
                    + "active text" + ");");

            db.execSQL("create table table_plans ("
                    + "id integer primary key autoincrement,"
                    + "category text,"
                    + "name text,"
                    + "time text,"
                    + "active text"
                    + ");");
            db.execSQL("create table table_steps ("
                    + "id integer primary key autoincrement,"
                    + "hp text,"
                    + "name text,"
                    + "time text,"
                    + "active text"
                    + ");");
            db.execSQL("create table table_user_day ("
                    + "id integer primary key autoincrement,"
                    + "whatday text,"
                    + "importantdeal text,"
                    + "remember text,"
                    + "newexpereance text,"
                    + "time text"
                    + ");");
            db.execSQL("create table table_category ("
                    + "id integer primary key autoincrement,"
                    + "category text,"
                    + "nametask text,"
                    + "time text,"
                    + "active text"
                    + ");");
            db.execSQL("create table table_well_done_tasks ("
                    + "id integer primary key autoincrement,"
                    + "name text,"
                    + "data text"
                    + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        }

    }


}

