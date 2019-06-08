package com.example.p.jumptime.Activity;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.p.jumptime.Fragment.AddTask;
import com.example.p.jumptime.Controller.AlarmReceiver;
import com.example.p.jumptime.Fragment.Article;
import com.example.p.jumptime.Fragment.Categories;
import com.example.p.jumptime.Controller.DataBase;
import com.example.p.jumptime.Fragment.Goal2;
import com.example.p.jumptime.Fragment.Home;
import com.example.p.jumptime.Fragment.Motivation;
import com.example.p.jumptime.Fragment.Statistic;
import com.example.p.jumptime.R;
import com.example.p.jumptime.Model.TaskForRecyclerView;
import com.example.p.jumptime.Fragment.TasksForCurrentPerfomance;
import com.example.p.jumptime.Fragment.TimePlans;
import com.example.p.jumptime.Fragment.UserDay;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import static java.util.Calendar.*;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //хранятся дела пользователя
    ArrayList<TaskForRecyclerView> tasks;
    ArrayList sizeArray = new ArrayList();

    //отвечают за создание уведомлений при входе
    private PendingIntent pendingIntent;
    int counter = 0;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //проверка вошел ли пользователь
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            //нет пользователя открытие LoginActivity
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        tasks = new ArrayList();

        //Создание уведомлений
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        start();
        startAt10();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //левое выдвижное меню
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //чтение списка дел и заполнение в листы
        try {
            sizeArray = GetListTask();
            getArray(sizeArray);
            sortedArrayToDate();
            sortedByDate();
        } catch (NullPointerException w) {

        }

        //поток в котором создаются уведомления
        Thread hread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < tasks.size(); i++) {
                    String myDate2 = tasks.get(i).getTaskData() + " " + tasks.get(i).getTaskTime();

                    if (isNeed(tasks.get(i).getTaskData(), tasks.get(i).getTaskTime())) {

                        setApplicationNotification(myDate2, tasks.get(i).getTaskName());
                    }

                }
            }
        });
        hread.start();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // устанавливаем начальный фрагмент - Home
        Fragment fragment = null;
        Class fragmentClass = Home.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        assert fragment != null;
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
    }

    //проверяет две даты на истекшее время
    public static boolean isNeed(String data1, String time) {
        DateFormat dateFormat2 = new SimpleDateFormat("kk:mm", Locale.getDefault());
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String timet = dateFormat2.format(new Date());
        String[] i = timet.split(":");
        String data = dateFormat.format(new Date());
        String[] subDate = data.split("\\.");
        String[] subStr;
        String[] timeq = time.split(":");
        subStr = data1.split("\\.");
        //если дата прошедшая
        if (Integer.valueOf(subDate[2]) >= Integer.valueOf(subStr[2]) && Integer.valueOf(subDate[1]) >= Integer.valueOf(subStr[1]) && Integer.valueOf(subDate[0]) > Integer.valueOf(subStr[0])) {
            return true;
        }
        // дата сегодняшняя, определяется по времени
        if (Integer.valueOf(subStr[0]) == Integer.valueOf(subDate[0])) {

            if (Integer.valueOf(timeq[0]) < Integer.valueOf(i[0])) {
                return true;

            } else if (Integer.valueOf(timeq[0]) == Integer.valueOf(i[0])) {
                if (Integer.valueOf(timeq[1]) <= Integer.valueOf(i[1])) {
                    return true;
                }
            }
        }
        return false;
    }

    public void start() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 8000;
        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);

    }

    public void cancel() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
    }

    public void startAt10() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 1000 * 60 * 20;

        /* установка "Будильника" на 10:30*/
        Calendar calendar = getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(HOUR_OF_DAY, 10);
        calendar.set(MINUTE, 30);
        /* 20 minutes интервал */
       /* manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 60 * 20, pendingIntent);*/
    }

    //метод читает список задач из бд sqlite
    public ArrayList GetListTask() {

        ContentValues cv = new ContentValues();

        DataBase.DBHelper dbHelper = new DataBase.DBHelper(this);
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


            } while (c.moveToNext());
        } else
            Log.d("TAG", "0 rows");
        c.close();

        return listtasks;
    }


    public  void setApplicationNotification(String myDate, String name) {
        Calendar c = new GregorianCalendar();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        Date date = null;
        try {
            date = sdf.parse(myDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        long mills = date.getTime();
        Resources res = this.getResources();
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.ic_dialog_email)
                        .setContentTitle("Пора решить!")
                        .setContentText(name);


        builder.setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_icon1));
        builder.setAutoCancel(true); // автоматически закрыть уведомление после нажатия

        builder.setWhen(mills);
        builder.setShowWhen(false);
        Notification notification = builder.build();

        notification.defaults = Notification.DEFAULT_ALL;

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(counter, notification);
        counter++;


    }

    private void sortedByDate() {
        Calendar c = new GregorianCalendar();
        int cMinute = c.get(MINUTE);
        int cHour = c.get(HOUR_OF_DAY);

        ArrayList<TaskForRecyclerView> Last = new ArrayList<>();
        ArrayList<TaskForRecyclerView> Future = new ArrayList<>();

        for (int i = 0; i < tasks.size(); i++) {
            String[] sub = tasks.get(i).getTaskTime().split(":");
            if (Integer.valueOf(sub[0]) < (cHour)) Last.add(tasks.get(i));
            else if (Integer.valueOf(sub[0]) == (cHour) && Integer.valueOf(sub[1]) <= cMinute)
                Last.add(tasks.get(i));
            else Future.add(tasks.get(i));

        }
        tasks.clear();

        for (int i = 0; i < Last.size(); i++) {
            tasks.add(Last.get(i));
        }

        for (int i = 0; i < Future.size(); i++) {
            tasks.add(Future.get(i));
        }

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

            if (Integer.valueOf(subDate[2]) >= Integer.valueOf(subStr[2]) && Integer.valueOf(subDate[1]) >= Integer.valueOf(subStr[1]) && Integer.valueOf(subDate[0]) > Integer.valueOf(subStr[0]))
                Last.add(tasks.get(i));
            else if (Integer.valueOf(subDate[2]) == (Integer.valueOf(subStr[2])) && Integer.valueOf(subDate[1]) == (Integer.valueOf(subStr[1])) && Integer.valueOf(subDate[0]) == (Integer.valueOf(subStr[0])))
                ToDay.add(tasks.get(i));
            else if (Integer.valueOf(subDate[2]) <= Integer.valueOf(subStr[2]) && Integer.valueOf(subDate[1]) <= Integer.valueOf(subStr[1]) && Integer.valueOf(subDate[0]) < Integer.valueOf(subStr[0]))
                Future.add(tasks.get(i));
            else ToDay.add(tasks.get(i));

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
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // выбираем цвет шторки
        drawer.setScrimColor(Color.TRANSPARENT);

        if (drawer.isDrawerOpen(GravityCompat.START)) {

            drawer.closeDrawer(Gravity.LEFT);
        } else {
            drawer.openDrawer(Gravity.LEFT);
        }

    }



    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;
        int id = item.getItemId();

        if (id == R.id.nav_goal) {
            fragment = new Goal2();
        } else if (id == R.id.nav_tasks) {
            fragment = new TasksForCurrentPerfomance();

        } else if (id == R.id.nav_addTask) {
            fragment = new AddTask();
        } else if (id == R.id.time_plan) {
            fragment = new TimePlans();
        } else if (id == R.id.nav_project_category) {
            fragment = new Categories();
        } else if (id == R.id.nav_my_day) {
            fragment = new UserDay();

        } else if (id == R.id.nav_time) {
            fragment = new Motivation();
        } else if (id == R.id.nav_exit) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_time_manadgment) {
            fragment = new Article();
        } else if (id == R.id.nav_comand) {
            fragment = new Statistic();
        }
        if (fragment != null) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            // Выделяем выбранный пункт меню в шторке
            // item.setChecked(true);
            // Выводим выбранный пункт в заголовке
            setTitle(item.getTitle());

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);


        }
        return true;

    }

    public void getArray(ArrayList ar) {
        if (ar.isEmpty()) {
        } else {
            for (int i = 0; i < ar.size(); i++) {
                ArrayList<ArrayList> temp = (ArrayList) ar.get(i);
                Log.d("Tag", Integer.valueOf(String.valueOf(temp.get(8))).getClass() + "");
                int index = Integer.valueOf(String.valueOf(temp.get(8)));
                int index2 = Integer.valueOf(String.valueOf(temp.get(8)));
                tasks.add(new TaskForRecyclerView(String.valueOf(temp.get(1)), String.valueOf(temp.get(2)), String.valueOf(temp.get(3)), TasksForCurrentPerfomance.image_priority[index], Integer.valueOf(String.valueOf(temp.get(0))), TasksForCurrentPerfomance.imgid[index2], MainActivity.this));
                temp.clear();
            }
        }
    }
}

