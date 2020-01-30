package com.example.p.jumptime.Fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.p.jumptime.R;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class DiaryFragment extends Fragment {
    private String nameOfAims;
    private String descriptionOfAims;
    private String timeOfAims;
    private int idColorBack;
    private int idColorFront;
    private BroadcastReceiver broadcastReceiverAimsPrimary;
    private BroadcastReceiver broadcastReceiverAimsLow;
    private Bundle bundle = new Bundle();

    private ListView listViewWithCheckBox;
    //private List<AimsDiaryCheckBoxBolvanka> listViewItems;
    private Context mContext;
    private Bundle dataFrom;
    private int x;
    private String starkS ="top";
    private Cursor cursor;
   // private DatabaseForAllAimsHelper  mHelper;
    private Cursor cursorHigh;
   // private DatabaseForAllAimsHighHelper  mHelperHigh;
    private Cursor cursorMedium;
  //  private DatabaseForAllAimsMediumHelper  mHelperMedium;
    private Cursor cursorLow;
   // private DatabaseForAllAimsLowHelper  mHelperLow;
    private Toolbar toolbar;

    private ArrayList<String> taskList = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private DrawerLayout drawer;
    private String prioraPrimary = "Primary";
    private String prioraHigh = "High";
    private String prioraMedium = "Mediem";
    private String prioraLow = "Low";

    public DiaryFragment() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mContext = context;
        Log.d("HOPELessPP", String.valueOf(listViewWithCheckBox));
       // Log.d("HOPELessPP", String.valueOf(listViewItems));



    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View RootView1 = inflater.inflate(R.layout.fragment_diary, container, false);






        return RootView1;


    }



    public static int randInt(int min, int max) {

        // Usually this can be a field rather than a method variable
        Random rand = new Random();
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

}
