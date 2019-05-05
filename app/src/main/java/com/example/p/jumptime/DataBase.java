package com.example.p.jumptime;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DataBase extends Fragment implements OnClickListener {
    static final String LOG_TAG = "myLogs";

    Button btnAdd, btnRead, btnClear, btnUpd, btnDel;
    EditText etName, etEmail, etID;
     static ArrayList<ArrayList> listtasks;
    static DBHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    /**
     * Called when the activity is first created.
     */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.base, container, false);

        btnAdd = (Button) rootView.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnRead = (Button) rootView.findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);

        btnClear = (Button) rootView.findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        btnUpd = (Button) rootView.findViewById(R.id.btnUpd);
        btnUpd.setOnClickListener(this);

        btnDel = (Button) rootView.findViewById(R.id.btnDel);
        btnDel.setOnClickListener(this);

        etName = (EditText) rootView.findViewById(R.id.etName);
        etEmail = (EditText) rootView.findViewById(R.id.etEmail);
        etID = (EditText) rootView.findViewById(R.id.etID);

        // создаем объект для создания и управления версиями БД
        dbHelper = new DBHelper(getContext());
        return rootView;
    }

    public void onClick(View v) {
        // создаем объект для данных
        // получаем данные из полей ввода
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String id = etID.getText().toString();

        ContentValues cv = new ContentValues();
        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        switch (v.getId()) {
            case R.id.btnAdd:
                ContentValues cvq = new ContentValues();
                // подключаемся к БД
                SQLiteDatabase dbq = dbHelper.getWritableDatabase();

                cvq.put("category", "month");
                cvq.put("name" , "write book");
                cvq.put("time","21.2.2019");
                cvq.put("active", "1");
                // вставляем запись и получаем ее ID
                long rowID = dbq.insert("table_plans", null, cvq);
                Log.d(LOG_TAG, "row inserted, ID = " + rowID);
                Toast.makeText(getContext(), "row inserted, ID = " + rowID, Toast.LENGTH_SHORT).show();



             /*   Log.d(LOG_TAG, "--- Insert in mytable: ---");
                // подготовим данные для вставки в виде пар: наименование столбца -
                // значение
                cv.put("name", name);
                cv.put("data", email);
                cv.put("time", name);
                cv.put("k", email);
                cv.put("i", name);
                cv.put("l", email);
                cv.put("o", name);
                cv.put("priority", email);
                cv.put("project", name);
                cv.put("active", email);
                // вставляем запись и получаем ее ID
                long rowID = db.insert("mytable", null, cv);
                Log.d(LOG_TAG, "row inserted, ID = " + rowID);*/
                break;
            case R.id.btnRead:
                readData();

                break;
            case R.id.btnClear:
                Log.d(LOG_TAG, "--- Clear mytable: ---");
                listtasks.clear();
                // удаляем все записи
                int clearCount = db.delete("mytable", null, null);
                Log.d(LOG_TAG, "deleted rows count = " + clearCount);
                break;
            case R.id.btnUpd:
                if (id.equalsIgnoreCase("")) {
                    break;
                }
                Log.d(LOG_TAG, "--- Update mytable: ---");
                // подготовим значения для обновления
                cv.put("name", name);
                cv.put("data", email);
                cv.put("time", name);
                cv.put("k", email);
                cv.put("i", name);
                cv.put("l", email);
                cv.put("o", name);
                cv.put("priority", email);
                cv.put("project", name);
                cv.put("active", email);
                // обновляем по id
                int updCount = db.update("mytable", cv, "id = ?",
                        new String[]{id});
                Log.d(LOG_TAG, "updated rows count = " + updCount);
                break;
            case R.id.btnDel:
                if (id.equalsIgnoreCase("")) {
                    break;
                }
                Log.d(LOG_TAG, "--- Delete from mytable: ---");
                // удаляем по id
                int delCount = db.delete("mytable", "id = " + id, null);
                Log.d(LOG_TAG, "deleted rows count = " + delCount);
                break;
        }
        // закрываем подключение к БД
        dbHelper.close();
    }

    public static void readData(){
        // создаем объект для данных
        ContentValues cv = new ContentValues();
        // создаем объект для создания и управления версиями БД

        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Log.d(LOG_TAG, "--- Rows in mytable: ---");
        // делаем запрос всех данных из таблицы mytable, получаем Cursor
        Cursor c = db.query("mytable", null, null, null, null, null, null);
        listtasks = new ArrayList();
        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex("name");
            int dataColIndex = c.getColumnIndex("data");
            int timeColIndex = c.getColumnIndex("time");
            int kColIndex = c.getColumnIndex("k");
            int iColIndex = c.getColumnIndex("i");
            int lColIndex = c.getColumnIndex("l");
            int oColIndex = c.getColumnIndex("o");
            int priorityColIndex = c.getColumnIndex("priority");
            int projectColIndex = c.getColumnIndex("project");
            int activeColIndex = c.getColumnIndex("active");


            do {
                ArrayList temp = new ArrayList();
                temp.add(c.getInt(idColIndex));
                temp.add(c.getString(nameColIndex));
                temp.add(c.getString(dataColIndex));
                temp.add(c.getString(timeColIndex));
                temp.add(c.getString(kColIndex));
                temp.add(c.getString(iColIndex));
                temp.add(c.getString(lColIndex));
                temp.add(c.getString(oColIndex));
                temp.add(c.getString(priorityColIndex));
                temp.add(c.getString(projectColIndex));
                temp.add(c.getString(activeColIndex));
                listtasks.add(temp);
                // получаем значения по номерам столбцов и пишем все в лог
                Log.d(LOG_TAG,
                        "ID = " + c.getInt(idColIndex) + ", name = "
                                + c.getString(nameColIndex) + ", email = "
                                + c.getString(dataColIndex));
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false -
                // выходим из цикла
            } while (c.moveToNext());
        } else
            Log.d(LOG_TAG, "0 rows");
        c.close();

    }
    static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            // конструктор суперкласса
            super(context, "myDB", null, 1);
        }

        public void onCreate(SQLiteDatabase db) {
            Log.d("LOG", "--- onCreate database ---");
            // создаем таблицу с полями
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
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }

    }


    }

