package com.example.p.jumptime;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class TimeTable extends Fragment {

    View view;
    ArrayList<TaskForSchedule> tasks;
    ArrayList ar;
    int[] image_label = {R.drawable.bell_icon, R.drawable.plus};

    public TimeTable() {
        tasks = new ArrayList<>();

    }

    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();

        view = inflater.inflate(R.layout.fragment_shedule_test, container, false);


        TabLayout tabLayout = view.findViewById(R.id.sliding_tabs);
        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText(""));
        tabLayout.addTab(tabLayout.newTab().setText(""));
        tabLayout.addTab(tabLayout.newTab().setText(""));
        tabLayout.addTab(tabLayout.newTab().setText(""));
        tabLayout.addTab(tabLayout.newTab().setText(""));
        tabLayout.addTab(tabLayout.newTab().setText(""));
        tabLayout.addTab(tabLayout.newTab().setText(""));
        //tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabTextColors(Color.parseColor("#00FF00"), Color.parseColor("#f00f0f"));
        //Initializing viewPager
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);

        //Creating our pager adapter
        PagerTimeTable adapter = new PagerTimeTable(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        //Adding onTabSelectedListener to swipe views
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



       // setElementNull();
      /*  Button add1 = view.findViewById(R.id.button_monday);
        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDelo("Понедельник");

            }
        });
        Button add2 = view.findViewById(R.id.button_tuesday);
        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDelo("Вторник");

            }
        });
        Button add3 = view.findViewById(R.id.button_wednesday);
        add3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDelo("Среда");

            }
        });
        Button add4 = view.findViewById(R.id.button_thursday);
        add4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDelo("Четверг");

            }
        });
        Button add5 = view.findViewById(R.id.button_friday);
        add5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDelo("Пятница");

            }
        });
        Button add6 = view.findViewById(R.id.button_saturday);
        add6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDelo("Суббота");

            }
        });
        Button add7 = view.findViewById(R.id.button_sunday);
        add7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDelo("Воскресенье");

            }
        });*/
        /*CoordinatorLayout coordinatorLayoutMonday = view.findViewById(R.id.coordinatorLayout_monday);
        CoordinatorLayout coordinatorLayoutTuesday = view.findViewById(R.id.coordinatorLayout_tuesday);
        CoordinatorLayout coordinatorLayoutWednesday = view.findViewById(R.id.coordinatorLayout_wednesday);
        CoordinatorLayout coordinatorLayoutThursday = view.findViewById(R.id.coordinatorLayout_thursday);
        CoordinatorLayout coordinatorLayoutFriday = view.findViewById(R.id.coordinatorLayout_friday);
        CoordinatorLayout coordinatorLayoutSaturday = view.findViewById(R.id.coordinatorLayout_saturday);
        CoordinatorLayout coordinatorLayoutSunday = view.findViewById(R.id.coordinatorLayout_sunday);*/


      //  initializeViews();



        return view;

    }

   /* private void TakeSizeNeighbour(){
        ArrayList time = new ArrayList();

        for (int i = 0; i < tasks.size(); i++) {
           time.add(tasks.get(i).getTaskData());
        }
        for (int i = 0; i < time.size(); i++) {
           // if(Integer.valueOf((Integer)(time.get(i))) ){}
        }


    }*/
  /*  private void addItemIntoListView(){
        tasks.add(new TaskForRecyclerView(ar.get(0)+"", ar.get(1)+"", ar.get(2) + "", image_label[Integer.valueOf((Integer) ar.get(3))],0, getActivity()));

    }*/
    private void addDelo(String day){
        Fragment fragment = new Skeleton();
        Bundle bundle = new Bundle();
        bundle.putString("key", day);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
    }
private  void initializeViews(){
    DataAdapterRecyclerViewToTimeTable adapter = new DataAdapterRecyclerViewToTimeTable(getContext(), tasks);
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    LinearLayoutManager layoutManager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    LinearLayoutManager layoutManager4 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    LinearLayoutManager layoutManager5 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    LinearLayoutManager layoutManager6 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    LinearLayoutManager layoutManager7 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

    RecyclerView recyclerViewMonday = view.findViewById(R.id.list_monday);
    recyclerViewMonday.setLayoutManager(layoutManager);
    recyclerViewMonday.setAdapter(adapter);

    RecyclerView recyclerViewTuesday = view.findViewById(R.id.list_tuesday);
    recyclerViewTuesday.setLayoutManager(layoutManager2);
    recyclerViewTuesday.setAdapter(adapter);

    RecyclerView recyclerViewWednesday = view.findViewById(R.id.list_wednesday);
    recyclerViewWednesday.setLayoutManager(layoutManager3);
    recyclerViewWednesday.setAdapter(adapter);

    RecyclerView recyclerViewThursday = view.findViewById(R.id.list_thursday);
    recyclerViewThursday.setLayoutManager(layoutManager4);
    recyclerViewThursday.setAdapter(adapter);

    RecyclerView recyclerViewFriday = view.findViewById(R.id.list_friday);
    recyclerViewFriday.setLayoutManager(layoutManager5);
    recyclerViewFriday.setAdapter(adapter);

    RecyclerView recyclerViewSaturday = view.findViewById(R.id.list_saturday);
    recyclerViewSaturday.setLayoutManager(layoutManager6);
    recyclerViewSaturday.setAdapter(adapter);

    RecyclerView recyclerViewSunday = view.findViewById(R.id.list_sunday);
    recyclerViewSunday.setLayoutManager(layoutManager7);
    recyclerViewSunday.setAdapter(adapter);

}
   /* public void setElementNull() {

        tasks.add(new TaskForRecyclerView("Добавьте дело", "укажите время", " ", R.drawable.plus,0, getActivity()));





    }*/
}
