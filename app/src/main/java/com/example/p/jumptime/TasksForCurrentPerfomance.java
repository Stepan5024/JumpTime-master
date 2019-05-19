package com.example.p.jumptime;


import android.app.Notification;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;


public class TasksForCurrentPerfomance extends Fragment {
    public static ArrayList<TaskForRecyclerView> tasks = new ArrayList<>();
    DataAdaptermy adapter;
    ArrayList sizeArray = new ArrayList();
    View rootView;
    List<String[]> array;
    ArrayList images;
    RecyclerView recyclerView;
    CoordinatorLayout coordinatorLayout;
    ArrayList arForAdapter;
    LinearLayoutManager layoutManager;
    static DataBase.DBHelper dbHelper;
    // Идентификатор уведомления
    public static final int NOTIFY_ID = 101;
    static int[] image_priority = {R.mipmap.white, R.mipmap.yellow, R.mipmap.orange, R.mipmap.red};

    public TasksForCurrentPerfomance() {
        tasks = new ArrayList<>();
        array = new ArrayList<>();
        images = new ArrayList();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_tasks_for_current, container, false);
        arForAdapter = new ArrayList();
        coordinatorLayout = rootView.findViewById(R.id.coordinatorLayout);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        Switch sw = rootView.findViewById(R.id.switchToCalendar);
        dbHelper = new DataBase.DBHelper(getContext());
        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new MyCalendarView();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            }
        });

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getContext())
                        .setSmallIcon(android.R.drawable.ic_dialog_email)
                        .setContentTitle("Title change")
                        .setContentText("Notification text change");

        Notification notification = builder.build();

        NotificationManager notificationManager =
                (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
        recyclerView = rootView.findViewById(R.id.list);
        recyclerView.setLayoutManager(layoutManager);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new AddTask();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            }
        });

        sizeArray = GetListTask();
        getArray(sizeArray);
        sortedArrayToDate();
        sortedByDate();
        adapter = new DataAdaptermy(getContext(), tasks);

        recyclerView.setAdapter(adapter);

        enableSwipeToDeleteAndUndo();
        dbHelper = new DataBase.DBHelper(getContext());
        return rootView;

    }

    public void updateUI() {
        if (getActivity() != null) {

            DataAdaptermy adapter = new DataAdaptermy(getContext(), tasks);
            recyclerView.setAdapter(adapter);

        }
    }
    private  void sortedByDate(){
        Calendar c = new GregorianCalendar();
        int cMinute = c.get(Calendar.MINUTE);
        int cHour = c.get(Calendar.HOUR_OF_DAY);
        ArrayList<TaskForRecyclerView> Last = new ArrayList<>();
        ArrayList<TaskForRecyclerView> Future = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            String[] sub = tasks.get(i).getTaskTime().split(":");

            if(Integer.valueOf(sub[0]) <(cHour)  ){


                Last.add(tasks.get(i));
            }
            else if (Integer.valueOf(sub[0]) == (cHour)  && Integer.valueOf(sub[1]) <= cMinute){
                Last.add(tasks.get(i));
            }
            else

            {    Future.add(tasks.get(i));}
        }
        tasks.clear();
        for (int i = 0; i < Last.size(); i++) {
            tasks.add(Last.get(i));
        }
        for (int i = 0; i < Future.size(); i++) {
            tasks.add(Future.get(i));
        }
        updateUI();
    }
    private void sortedArrayToDate() {


        // Текущее время
        Date currentDate = new Date();
        // Форматирование времени как "день.месяц.год"
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String[] subDate;

        ArrayList<TaskForRecyclerView> Last = new ArrayList();
        ArrayList<TaskForRecyclerView> Future = new ArrayList();
        ArrayList<TaskForRecyclerView> ToDay = new ArrayList();
        String data = dateFormat.format(currentDate);
        subDate = data.split("\\.");
        for (int i = 0; i < tasks.size(); i++) {
            String t = tasks.get(i).getTaskData();

            String[] subStr;
            subStr = t.split("\\."); // Разделения строки str с помощью метода split()
            if (Integer.valueOf(subDate[2]) >= Integer.valueOf(subStr[2]) && Integer.valueOf(subDate[1]) >= Integer.valueOf(subStr[1]) && Integer.valueOf(subDate[0]) > Integer.valueOf(subStr[0])) {
                Last.add(tasks.get(i));
            } else if (Integer.valueOf(subDate[2]) == (Integer.valueOf(subStr[2])) && Integer.valueOf(subDate[1]) == (Integer.valueOf(subStr[1])) && Integer.valueOf(subDate[0]) == (Integer.valueOf(subStr[0]))) {
                ToDay.add(tasks.get(i));
            } else if (Integer.valueOf(subDate[2]) <= Integer.valueOf(subStr[2]) && Integer.valueOf(subDate[1]) <= Integer.valueOf(subStr[1]) && Integer.valueOf(subDate[0]) < Integer.valueOf(subStr[0]))
                Future.add(tasks.get(i));
            else {
                ToDay.add(tasks.get(i));
            }
        }


        tasks.clear();
        for (int i = 0; i < Last.size(); i++) {

            tasks.add(Last.get(i));
        }
        for (int i = 0; i < ToDay.size(); i++) {
            tasks.add(ToDay.get(i));
        }
        for (int i = 0; i < Future.size(); i++) {
            tasks.add(Future.get(i));
        }
        updateUI();


    }



    //метод читает список задач из бд sqlite
    public static ArrayList GetListTask() {
        //  объект для данных
        ContentValues cv = new ContentValues();
        // объект для создания и управления версиями БД

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // делаем запрос всех данных из таблицы mytable
        Cursor c = db.query("mytable", null, null, null, null, null, null);
        ArrayList listtasks = new ArrayList();

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

                // текущая - последняя, то false -
                // выходим из цикла
            } while (c.moveToNext());
        } else
            Log.d("TAG", "0 rows");
        c.close();

        return listtasks;
    }

    public  void getArray(ArrayList ar) {
        if (ar.isEmpty()) {
        } else {
            for (int i = 0; i < ar.size(); i++) {
                ArrayList<ArrayList> temp = (ArrayList) ar.get(i);
                Log.d("Tag", Integer.valueOf(String.valueOf(temp.get(8))).getClass() + "");
                int index = Integer.valueOf(String.valueOf(temp.get(8)));
                //Toast.makeText(getContext(), "size =  " + temp.size() + " /" + temp.get(8) + "/", Toast.LENGTH_SHORT).show();
                tasks.add(new TaskForRecyclerView(String.valueOf(temp.get(1)), String.valueOf(temp.get(2)), String.valueOf(temp.get(3)), image_priority[index], Integer.valueOf(String.valueOf(temp.get(0))), getActivity()));
                temp.clear();
            }
        }
    }


    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                final int position = viewHolder.getAdapterPosition();

                DataBase.DBHelper dbHelper = new DataBase.DBHelper(getContext());

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Log.d("LOG", "--- Delete from mytable: ---");
                // удаляем по id
                TaskForRecyclerView temp = tasks.get(position);


                int delCount = db.delete("mytable", "id = " + temp.getID(), null);
                Log.d("LOG", "deleted rows count = " + delCount);
                adapter.removeItem(position);
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        try {


                            adapter.restoreItem(tasks.get(0), position);

                        } catch (IndexOutOfBoundsException ex) {

                        }
                        recyclerView.scrollToPosition(position);
                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }


}