package com.example.p.jumptime;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import static com.example.p.jumptime.TasksForCurrentPerfomance.NOTIFY_ID;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        setApplicationNotification();
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
    public  void setApplicationNotification(){


        Resources res = this.getResources();
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_icon1)
                        .setContentTitle("Title")
                        .setContentText("Notification text");
        builder.setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_icon1)); // большая фото дела

        builder.setAutoCancel(true); // автоматически закрыть уведомление после нажатия
        builder.setWhen(System.currentTimeMillis()+2000);
        Notification notification = builder.build();

        notification.defaults = Notification.DEFAULT_ALL;

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);

    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void sendActionNotification(View view) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // Намерение для запуска второй активности
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // Строим уведомление
        Notification builder = new Notification.Builder(this)
                .setTicker("Пришла посылка!")
                .setContentTitle("Посылка")
                .setContentText(
                        "Это я, почтальон Печкин. Принес для вас посылку")
                .setSmallIcon(R.mipmap.ic_launcher).setContentIntent(pendingIntent)
                .addAction(R.mipmap.ic_launcher, "Открыть", pendingIntent)
                .addAction(R.mipmap.ic_launcher, "Отказаться", pendingIntent)
                .addAction(R.mipmap.ic_launcher, "Другой вариант", pendingIntent)
                .build();

        // убираем уведомление, когда его выбрали
        builder.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, builder);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // выбираем цвет шторки
        drawer.setScrimColor(Color.TRANSPARENT);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            //   Toast.makeText(MainActivity.this,"test",Toast.LENGTH_SHORT).show();
            drawer.closeDrawer(Gravity.LEFT);
        } else {
            drawer.openDrawer(Gravity.LEFT);
        }

        // super.onBackPressed();
        // openQuitDialog();
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;
        int id = item.getItemId();

        if (id == R.id.nav_goal) {
            fragment = new Goal2();
        } else if (id == R.id.nav_tasks) {
            fragment = new TasksForCurrentPerfomance();

        }
        else if (id == R.id.nav_addTask) {
            fragment = new AddTask();
        }
        else if (id == R.id.time_plan) {
            fragment = new TimePlans();
        }
        else if (id == R.id.nav_project_category) {
            fragment = new Categories();
        }
        else if (id == R.id.nav_comand) {
            fragment = new DataBase();
        }
        else if (id == R.id.nav_my_day) {
            fragment = new UserDay();
        }
        else if (id == R.id.nav_plan_day) {
            fragment = new TimeTable();
        }
        else if (id == R.id.nav_time) {
            fragment = new Motivation();
        }

        else if (id == R.id.nav_exit) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            // fragment = new Test();
        }
        if (fragment != null) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            // Выделяем выбранный пункт меню в шторке
           // item.setChecked(true);
            // Выводим выбранный пункт в заголовке
            setTitle(item.getTitle());

                DrawerLayout drawer =  findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);


        }
        return true;

    }
}

