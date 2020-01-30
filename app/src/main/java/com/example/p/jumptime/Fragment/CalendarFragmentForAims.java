package com.example.p.jumptime.Fragment;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;


import com.example.p.jumptime.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.p.jumptime.Fragment.DiaryFragment.randInt;


public class CalendarFragmentForAims extends android.support.v4.app.Fragment {

    private String nameOfAims;
    private String descriptionOfAims;
    private String timeOfAims;
    private int idColorBack;
    private int idColorFront;
    private int NewDay;
    private int iconBottomSheetId;
    private int colorBottomSheetId;
    private String iko = "Nigers";
    private int counter=0;
    private String Close;
    private BottomSheetDialogFragment bottomSheetDialogFragment;
    private int position;
    private int year, month, day;
    private Fragment me = this;
    private Context mContext;
   // private DatabaseForCalendarFinalHelper mHelperFinal;
    private String priora;
    private String starkS ="top";
    private int hulk = 0;
    private String thor = "thor";
    private String captain = "captain";
    private int vdova = 1;
    private String date_of_day;
    private String date = "date";

    private BroadcastReceiver broadcastReceiverAimsCalendarHigh;
    private BroadcastReceiver broadcastReceiverAimsCalendarMedium;
    private BroadcastReceiver broadcastReceiverAimsCalendarLow;
    private BroadcastReceiver broadcastReceiverData;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        broadcastReceiverData =  new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                NewDay = intent.getExtras().getInt("position");
                Log.d("KISA", String.valueOf(NewDay)+" it's ");
            }
        };
        LocalBroadcastManager.getInstance(mContext).registerReceiver(broadcastReceiverData, new IntentFilter("NewDay"));

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View RootView1 = inflater.inflate(R.layout.fragment_calendar_for_aims, container, false);
        LinearLayout linearLayout = (LinearLayout)RootView1.findViewById(R.id.axe);
        String[] colorsForDiary = getResources().getStringArray(R.array.mdcolor_random);
        String color = colorsForDiary[randInt(0, (colorsForDiary.length - 1))];
        linearLayout.setBackgroundColor(Color.parseColor(color));




        ListView listViewNewDay = (ListView)RootView1.findViewById(R.id.listView_for_calendar_aim);
        List<ListViewPrioritetBolvanka> listViewItems = new ArrayList<ListViewPrioritetBolvanka>();

        listViewItems.add(new ListViewPrioritetBolvanka(starkS,captain,thor,hulk,vdova));
        listViewNewDay.setAdapter(new ListViewPrioritetAdapter(mContext, listViewItems));

        broadcastReceiverAimsCalendarHigh =  new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    Log.d("HOPEE", "Done");
                    nameOfAims = intent.getExtras().getString("NewName");
                    descriptionOfAims = intent.getExtras().getString("NewDescription");
                    timeOfAims = intent.getExtras().getString("NewTime");
                    colorBottomSheetId = intent.getExtras().getInt("NewIdColorBack");
                    iconBottomSheetId = intent.getExtras().getInt("NewIdColorFront");
                    priora = intent.getExtras().getString("Priora");
                    date_of_day = String.valueOf(intent.getExtras().getInt("DateOfDay"));


                    Log.d("AKI", String.valueOf(NewDay));
                    Log.d("AKI", String.valueOf(priora)+" is me");

                    ContentValues values = new ContentValues();

                }
            };

            LocalBroadcastManager.getInstance(mContext).registerReceiver(broadcastReceiverAimsCalendarHigh, new IntentFilter("NewAimsHighCalendar"));

           broadcastReceiverAimsCalendarMedium =  new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    Log.d("HOPEE", "Done");
                    nameOfAims = intent.getExtras().getString("NewName");
                    descriptionOfAims = intent.getExtras().getString("NewDescription");
                    timeOfAims = intent.getExtras().getString("NewTime");
                    colorBottomSheetId = intent.getExtras().getInt("NewIdColorBack");
                    iconBottomSheetId = intent.getExtras().getInt("NewIdColorFront");
                    priora = intent.getExtras().getString("Priora");
                    date_of_day = String.valueOf(intent.getExtras().getInt("DateOfDay"));
                    Log.d("AKI", String.valueOf(NewDay));
                    Log.d("AKI", String.valueOf(priora)+" is me");

                    ContentValues values = new ContentValues();


                }
            };

            LocalBroadcastManager.getInstance(mContext).registerReceiver(broadcastReceiverAimsCalendarMedium, new IntentFilter("NewAimsMediumCalendar"));

            broadcastReceiverAimsCalendarLow=  new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    Log.d("HOPEE", "Done");
                    nameOfAims = intent.getExtras().getString("NewName");
                    descriptionOfAims = intent.getExtras().getString("NewDescription");
                    timeOfAims = intent.getExtras().getString("NewTime");
                    colorBottomSheetId = intent.getExtras().getInt("NewIdColorBack");
                    iconBottomSheetId = intent.getExtras().getInt("NewIdColorFront");
                    priora = intent.getExtras().getString("Priora");
                    date_of_day = String.valueOf(intent.getExtras().getInt("DateOfDay"));

                    Log.d("AKI", String.valueOf(NewDay));
                    Log.d("AKI", String.valueOf(priora)+" is me");
                    ContentValues values = new ContentValues();


                }
            };

            LocalBroadcastManager.getInstance(mContext).registerReceiver(broadcastReceiverAimsCalendarLow, new IntentFilter("NewAimsLowCalendar"));

            BroadcastReceiver broadcastReceiverShutDown =  new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("AKI", "It's me");
                if(broadcastReceiverAimsCalendarHigh!= null){
                    LocalBroadcastManager.getInstance(mContext).unregisterReceiver(broadcastReceiverAimsCalendarHigh);
                }
                if(broadcastReceiverAimsCalendarMedium!= null){
                    LocalBroadcastManager.getInstance(mContext).unregisterReceiver(broadcastReceiverAimsCalendarMedium);
                }
                if(broadcastReceiverAimsCalendarLow!= null){
                    LocalBroadcastManager.getInstance(mContext).unregisterReceiver(broadcastReceiverAimsCalendarLow);
                }
                if(broadcastReceiverData!= null){
                    LocalBroadcastManager.getInstance(mContext).unregisterReceiver(broadcastReceiverData);
                }

                }
            };
            LocalBroadcastManager.getInstance(mContext).registerReceiver(broadcastReceiverShutDown, new IntentFilter("NewDay"));




        return RootView1;

    }
    @Override
    public void onStop() {
        super.onStop();
        Log.d("HOPEVV_MAIN", "onStop");

    }
    @Override
    public void onPause() {
        super.onPause();


        Log.d("HOPEVV_MAIN_MAMBA", "onPause");
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(broadcastReceiverAimsCalendarHigh!= null){
            LocalBroadcastManager.getInstance(mContext).unregisterReceiver(broadcastReceiverAimsCalendarHigh);
        }
        if(broadcastReceiverAimsCalendarMedium!= null){
            LocalBroadcastManager.getInstance(mContext).unregisterReceiver(broadcastReceiverAimsCalendarMedium);
        }
        if(broadcastReceiverAimsCalendarLow!= null){
            LocalBroadcastManager.getInstance(mContext).unregisterReceiver(broadcastReceiverAimsCalendarLow);
        }
        if(broadcastReceiverData!= null){
            LocalBroadcastManager.getInstance(mContext).unregisterReceiver(broadcastReceiverData);
        }


    }
    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d("HOPEVV_MAIN_MAMBA", "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

}
