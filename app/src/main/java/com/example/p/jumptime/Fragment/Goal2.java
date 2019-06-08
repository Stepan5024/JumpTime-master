package com.example.p.jumptime.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.p.jumptime.Activity.LoginActivity;
import com.example.p.jumptime.Controller.Pager;
import com.example.p.jumptime.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/*
 *
 * Отображение мечты, представление информации КИЛО, о шагах, график выполненных дел за неделю
 * */
public class Goal2 extends Fragment {
    View view;
    private Button newGoal;
    String goal;
    String k;
    String i;
    String l;
    String dataEnd;
    ArrayList<String> achiv;
    ArrayList<String> step;
    ArrayAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_goal2, container, false);

        final TextView tv_goal = view.findViewById(R.id.textView23);

        initElements();


        // Передаём ViewPager в TabLayout
        TabLayout tabLayout = view.findViewById(R.id.sliding_tabs);

        tabLayout.addTab(tabLayout.newTab().setText(""));
        tabLayout.addTab(tabLayout.newTab().setText(""));
        tabLayout.addTab(tabLayout.newTab().setText(""));


        tabLayout.setTabTextColors(Color.parseColor("#469232"), Color.parseColor("#B71C1C"));

        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);


        Pager adapter = new Pager(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        //установка adapter to pager
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        //метод прослушивания свайпа по view
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


        newGoal = view.findViewById(R.id.butn_new_goal);
        newGoal.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onClick(View view) {

                Fragment fragment = new AddGoal();
                FragmentManager fragmentManager = getFragmentManager();
                if (fragmentManager != null) {
                    fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                }
            }
        });


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference myRef;
        FirebaseDatabase database = FirebaseDatabase.getInstance();


        if (user != null) {
            myRef = database.getReference();
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        int count = Integer.parseInt(dataSnapshot.child("users").child(user.getUid()).child("infoUser").child("achiv").child("counter").getValue(String.class));
                        for (int j = 0; j < count; j++) {
                            achiv.add(dataSnapshot.child("users").child(user.getUid()).child("infoUser").child("achiv").child(j + "").getValue(String.class));
                        }
                        int count2 = Integer.parseInt(dataSnapshot.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child("counter").getValue(String.class));
                        for (int j = 0; j < count2; j++) {
                            step.add(dataSnapshot.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child(j + "").getValue(String.class));
                        }
                        goal = dataSnapshot.child("users").child(user.getUid()).child("infoUser").child("goal").getValue(String.class);
                        dataEnd = dataSnapshot.child("users").child(user.getUid()).child("infoUser").child("dataEnd").getValue(String.class);
                        k = dataSnapshot.child("users").child(user.getUid()).child("infoUser").child("k").getValue(String.class);
                        i = dataSnapshot.child("users").child(user.getUid()).child("infoUser").child("i").getValue(String.class);
                        l = dataSnapshot.child("users").child(user.getUid()).child("infoUser").child("l").getValue(String.class);
                        tv_goal.setText(goal);

                    } catch (Exception e) {

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.w("TAG", "onCancelled", databaseError.toException());
                }
            });
        } else {

            Toast.makeText(getContext(), "нет user, авторизуйтесь", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);

        }

        return view;
    }

    void initElements() {
        achiv = new ArrayList();
        step = new ArrayList();
    }
}

