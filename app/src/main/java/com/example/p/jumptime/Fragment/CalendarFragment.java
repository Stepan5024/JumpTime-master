package com.example.p.jumptime.Fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.p.jumptime.R;


/**
 * A simple {@link Fragment} subclass.
 */


public class CalendarFragment extends android.support.v4.app.Fragment {

    private String nameOfAims;
    private String descriptionOfAims;
    private String timeOfAims;
    private int idColorBack;
    private int idColorFront;
    private String NewDay;
    private int iconBottomSheetId;
    private int colorBottomSheetId;
    private String iko = "Nigers";
    private int counter=0;
    private String Close;

    private int position;
    private int year, month, day;
   // private DatabaseForCalendarHelper mHelper;
    private Context mContext;
    private String date;


    public CalendarFragment() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;


    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View RootView1 = inflater.inflate(R.layout.fragment_calendar, container, false);

        RelativeLayout relativeLayoutParent = RootView1.findViewById(R.id.calendar_new_day);
        ((FloatingActionButton) RootView1.findViewById(R.id.fab_add_in_calendar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenBottomSheet();
                counter = counter +1;
                Log.d("Slime", "I'm possible: "+counter );
            }
        });




        BroadcastReceiver broadcastReceiverShutDown =  new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {


                Close = intent.getExtras().getString("Down");
                Log.d("Fate",Close);




            }
        };
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiverShutDown, new IntentFilter("ShutCalendar"));

        

        return RootView1;
    }

    public  void OpenBottomSheet(){

    }

}
