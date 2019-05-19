package com.example.p.jumptime;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        Calendar c = new GregorianCalendar();
        int cMinute = c.get(Calendar.MINUTE);
        int cHour = c.get(Calendar.HOUR_OF_DAY);

        //if (cMinute == tMinute && cHour == tHour){}
        // For our recurring task, we'll just display a message
        Toast.makeText(context, "I'm running", Toast.LENGTH_SHORT).show();
    }
}