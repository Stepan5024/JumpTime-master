package com.example.p.jumptime.Fragment;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.p.jumptime.Controller.DataBase;
import com.example.p.jumptime.R;
import com.example.p.jumptime.Model.TaskForRecyclerView;


import java.util.ArrayList;
import java.util.HashMap;


/**
 * данный фрагмент будет использоваться в TabLayout в вкладке "График"
 * реализует построение графика по количеству выполненных дел на неделе
 */
public class Graph extends Fragment {
    ArrayList sizeArray;
    ArrayList<TaskForRecyclerView> tasks;
    ArrayList<String> datalist = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frgment_graph, container, false);


        sizeArray = GetListTask();
        //подсчет одинаковых дат в sizearray
        DoubleData();


        return view;

    }

    private void DoubleData() {
        HashMap<String, Integer> hm = new HashMap<String, Integer>();
        // Инициализирую счетчик
        Integer item;
        // прогоняю по циклу ArrayList и
        // закидываю дату в HashMap как ключ, а значение(Value) создаю или увеличиваю на 1
        for (String wrd : datalist) {


            item = hm.get(wrd);
            if (item == null) hm.put(wrd, 1); // если нет в списке то добавить со значением 1
            else hm.put(wrd, item + 1); // если есть такая дата(Key), то +1
        }


        /* for (Map.Entry entry : hm.entrySet()) {
           Toast.makeText(getContext(),"Key: " + entry.getKey() + " Value: "
                   + entry.getValue(),Toast.LENGTH_SHORT ).show();
        }*/
    }

    //метод читает список задач из бд sqlite
    private ArrayList GetListTask() {
        //  объект для данных
        ContentValues cv = new ContentValues();
        DataBase.DBHelper dbHelper = new DataBase.DBHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //запрос всех данных из таблицы mytable
        Cursor c = db.query("table_plans", null, null, null, null, null, null);
        ArrayList listtasks = new ArrayList();

        if (c.moveToFirst()) {

            // определяется номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex("name");
            int timeColIndex = c.getColumnIndex("time");
            int Category = c.getColumnIndex("category");
            int activeColIndex = c.getColumnIndex("active");


            do {
                ArrayList temp = new ArrayList();
                temp.add(c.getInt(idColIndex));
                temp.add(c.getString(nameColIndex));

                temp.add(c.getString(timeColIndex));
                temp.add(c.getString(Category));
                temp.add(c.getString(activeColIndex));
                listtasks.add(temp);
                datalist.add(c.getString(timeColIndex));

            } while (c.moveToNext());
        } else
            Log.d("TAG", "0 rows");
        c.close();

        return listtasks;
    }


}