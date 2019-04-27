package com.example.p.jumptime;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

public class TimePlans extends Fragment {

    View rootView;
    ArrayList arWeek;
    ArrayList arMonth;
    ArrayList arYear;
    ListView week;
    ListView month;
    ListView year;

    // это будет именем файла настроек
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_NAME = "Nickname"; // имя кота
    public static final String APP_PREFERENCES_AGE = "Age"; // возраст кота
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_tasks_for_user_plan, container, false);
        SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(getContext());


        week = rootView.findViewById(R.id.recycler_week);
        month = rootView.findViewById(R.id.recycler_month);
        year = rootView.findViewById(R.id.recycler_year);
        ImageView v1 = rootView.findViewById(R.id.imageView24);
        ImageView v2 = rootView.findViewById(R.id.imageView5);
        ImageView v3 = rootView.findViewById(R.id.imageView26);

        arWeek = new ArrayList();
        arMonth = new ArrayList();
        arYear = new ArrayList();

        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arWeek.add(" new task week");
                updateUI();
            }
        });
        v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arMonth.add(" new task moth");
                updateUI();
            }
        });
        v3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arYear.add(" new task year");
                updateUI();
            }
        });
        return rootView;
    }

    public void updateUI() {
        if (getActivity() != null) {

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.list_text_view, arWeek);
            week.setAdapter(adapter);
            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(), R.layout.list_text_view, arMonth);
            month.setAdapter(adapter1);
            ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getActivity(), R.layout.list_text_view, arYear);
            year.setAdapter(adapter2);
        }
    }
}
