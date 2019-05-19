package com.example.p.jumptime;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;


public class Goal2 extends Fragment {
    View view;


    private TextView textView;
    private Button newGoal;
    String goal;
    String k;
    String i;
    String l;
    String dataEnd;
    ArrayList achiv = new ArrayList();
    ArrayList step = new ArrayList();
    ArrayAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_goal2, container, false);

        textView = view.findViewById(R.id.text);
        final TextView tv_goal = view.findViewById(R.id.textView23);


        // lastBook.setAdapter(adapter);

       /* addStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
                final View uview = View.inflate(getContext(), R.layout.dialog_new_plan_task, null);
                builder.setView(uview);
                final android.support.v7.app.AlertDialog show = builder.show();

                Button ok = uview.findViewById(R.id.button6);
                final EditText ed_in_alertDialog = uview.findViewById(R.id.editText3);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //  indexMonth.add(saveInDataBase("month", ed_in_alertDialog.getText().toString()));
                        step.add(ed_in_alertDialog.getText().toString());
                        adapter.clear();
                        //adapter.addAll(step);
                        adapter.notifyDataSetChanged();

                        show.dismiss();
                    }
                });
            }
        });
*/
      /*  lastBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {


                if (position + 1 == step.size()) {
                    final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
                    final View uview = View.inflate(getContext(), R.layout.dialog_new_plan_task, null);
                    builder.setView(uview);
                    final android.support.v7.app.AlertDialog show = builder.show();

                    Button ok = uview.findViewById(R.id.button6);
                    final EditText ed_in_alertDialog = uview.findViewById(R.id.editText3);
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // indexMonth.add(saveInDataBase("month", ed_in_alertDialog.getText().toString()));
                            step.add(ed_in_alertDialog.getText().toString());
                            updateUI();
                            show.dismiss();
                        }
                    });
                    updateUI();
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    final View uview = View.inflate(getContext(), R.layout.dialog_field_punkt, null);
                    builder.setView(uview);
                    final AlertDialog show = builder.show();

                    Button ok = uview.findViewById(R.id.button_goodadd);
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            show.dismiss();
                        }
                    });
                }


            }
        });*/

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
            Toast.makeText(getContext(), "нет user", Toast.LENGTH_SHORT).show();
        }

        return view;
    }
}

