package com.example.p.jumptime.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Switch;

import com.example.p.jumptime.Controller.DataAdaptermy;
import com.example.p.jumptime.R;
import com.example.p.jumptime.Model.TaskForRecyclerView;

import java.util.ArrayList;
import java.util.Date;

/*
* Представление дел с помощью календаря
*
* */
public class MyCalendarView extends Fragment {


    RecyclerView recyclerView;
    DataAdaptermy adapter;
    ArrayList<TaskForRecyclerView> tasks;
    CoordinatorLayout coordinatorLayout;

    int[] image_priority = {R.mipmap.white, R.mipmap.yellow, R.mipmap.orange, R.mipmap.red};
    private Integer[] imgid = {
            R.mipmap.ic_icon1,
            R.drawable.i3,
            R.drawable.i4,
            R.drawable.i5,
            R.drawable.i6,
            R.drawable.i7,
            R.drawable.i1,
            R.drawable.i2,
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar_view, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        recyclerView = view.findViewById(R.id.list2);
        recyclerView.setLayoutManager(layoutManager);
        tasks = new ArrayList();

        coordinatorLayout = view.findViewById(R.id.coordinatorLayout);
        ArrayList sizeArray = TasksForCurrentPerfomance.GetListTask();
        getArray(sizeArray);

        Switch sw = view.findViewById(R.id.switch1);
        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new TasksForCurrentPerfomance();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            }
        });

        adapter = new DataAdaptermy(getContext(), tasks);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
        Date date = null;
        CalendarView calendarView = view.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year,
                                            int month, int dayOfMonth) {
                tasks.clear();
                int mYear = year;
                int mMonth = month;
                int mDay = dayOfMonth;
                String selectedDate = new StringBuilder().append(mDay)
                        .append(".").append(mMonth + 1).append(".").append(mYear).toString();


                sortedArrayToDay(selectedDate);
            }
        });

        return view;

    }

    private void sortedArrayToDay(String data) {
        ArrayList sizeArray = TasksForCurrentPerfomance.GetListTask();
        getArray(sizeArray);

        ArrayList<TaskForRecyclerView> thisDay = new ArrayList();
        String[] subDate;
        subDate = data.split("\\.");
        for (int i = 0; i < tasks.size(); i++) {
            String t = tasks.get(i).getTaskData();

            String[] subStr;
            subStr = t.split("\\."); // Разделения строки str с помощью метода split()

            if (Integer.valueOf(subDate[2]).compareTo(Integer.valueOf(subStr[2])) == 0 && Integer.valueOf(subDate[1]).compareTo(Integer.valueOf(subStr[1])) == 0 && Integer.valueOf(subDate[0]).compareTo(Integer.valueOf(subStr[0])) == 0) {
                thisDay.add(tasks.get(i));

            }


        }
        tasks.clear();
        for (int g = 0; g < thisDay.size(); g++) {

            tasks.add(thisDay.get(g));
        }
        updateUI();
    }

    public void updateUI() {
        if (getActivity() != null) {

            DataAdaptermy adapter = new DataAdaptermy(getContext(), tasks);
            recyclerView.setAdapter(adapter);

        }
    }

    public void getArray(ArrayList ar) {
        if (ar.isEmpty()) {
        } else {
            for (int i = 0; i < ar.size(); i++) {
                ArrayList<ArrayList> temp = (ArrayList) ar.get(i);
                Log.d("Tag", Integer.valueOf(String.valueOf(temp.get(8))).getClass() + "");
                int index = Integer.valueOf(String.valueOf(temp.get(8)));
                int index2 = Integer.valueOf(String.valueOf(temp.get(9)));
                tasks.add(new TaskForRecyclerView(String.valueOf(temp.get(1)), String.valueOf(temp.get(2)), String.valueOf(temp.get(3)), image_priority[index], Integer.valueOf(String.valueOf(temp.get(0))), imgid[index2], getActivity()));
                temp.clear();
            }
        }
    }
}