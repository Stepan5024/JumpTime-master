package com.example.p.jumptime;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.ListView;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Goal2 extends Fragment {
    View view;
    private SlidingUpPanelLayout slidingLayout;
    private Button btnShow;
    private ImageView btnHide;
    private TextView textView;
    private Button newGoal;
    String goal;
    String k;
    String i;
    String l;
    String dataEnd;
    ListView lastBook;
    ArrayList achiv = new ArrayList();
    ArrayList step = new ArrayList();
    private String tabTitles[] = new String[] { "КАЛЕНДАРЬ", "СПИСОК ДЕЛ"};
    //integer to count number of tabs
    int tabCount;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.test_pane, container, false);
        btnShow = view.findViewById(R.id.btn_show);
        btnHide = view.findViewById(R.id.btn_hide);
        textView = view.findViewById(R.id.text);
        final TextView tv_goal = view.findViewById(R.id.textView23);
        lastBook = view.findViewById(R.id.recycler_step);

        updateUI();
        lastBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {


                if (position + 1 == step.size()) {
                    Toast.makeText(getContext(), "добавить", Toast.LENGTH_SHORT).show();
                    step.add("fff2");
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

              /*  Fragment fragment = new Home();
                FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                fragmentIs = a10;*/
            }
        });
        // Получаем ViewPager и устанавливаем в него адаптер
       /*ViewPager viewPager = view.findViewById(R.id.viewpager);
        viewPager.setAdapter(
                new SampleFragmentPagerAdapter(getFragmentManager(), getContext()));
        tabLayout.setupWithViewPager(viewPager);*/
        // Передаём ViewPager в TabLayout
        TabLayout tabLayout = view.findViewById(R.id.sliding_tabs);
        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText(""));
        tabLayout.addTab(tabLayout.newTab().setText(""));
        tabLayout.addTab(tabLayout.newTab().setText(""));
        tabLayout.addTab(tabLayout.newTab().setText(""));
        //tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabTextColors(Color.parseColor("#00FF00"), Color.parseColor("#f00f0f"));
        //Initializing viewPager
       final ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        int[] imageResId = {
                R.drawable.plus, R.drawable.plus_orange
        };

        for (int i = 0; i < imageResId.length; i++) {
            tabLayout.getTabAt(i).setIcon(imageResId[i]);
        }
        //Creating our pager adapter
        Pager adapter = new Pager(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());

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

        //set layout slide listener
        slidingLayout = view.findViewById(R.id.sliding_layout);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference myRef;
        FirebaseDatabase database = FirebaseDatabase.getInstance();


        if (user != null) {
            myRef = database.getReference();


            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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
                    updateUI();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.w("TAG", "onCancelled", databaseError.toException());
                }
            });
        } else {
            Toast.makeText(getContext(), "нет user", Toast.LENGTH_SHORT).show();
        }


        //   goal = String.valueOf(DataBase.getData(ar,ar2).get(0));
        slidingLayout.setPanelSlideListener(onSlideListener());
        btnHide.setOnClickListener(onHideListener());
        btnShow.setOnClickListener(onShowListener());



        return view;
    }
    //Overriding method getItem


    private void updateUI() {
        if (getActivity() != null) {
            ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), R.layout.list_text_view, step);
            lastBook.setAdapter(adapter);

        }
    }
    /**
     * Request show sliding layout when clicked
     *
     * @return
     */
    private View.OnClickListener onShowListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show sliding layout in bottom of screen (not expand it)
                slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                btnShow.setVisibility(View.GONE);
            }
        };
    }

    /**
     * Hide sliding layout when click button
     *
     * @return
     */
    private View.OnClickListener onHideListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hide sliding layout
                slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
                btnShow.setVisibility(View.VISIBLE);
            }
        };
    }


    private SlidingUpPanelLayout.PanelSlideListener onSlideListener() {
        return new SlidingUpPanelLayout.PanelSlideListener() {

            @SuppressLint("SetTextI18n")
            public void onPanelSlide(View view, float v) {
                //  textView.setText("panel is sliding");
            }

            @SuppressLint("SetTextI18n")
            public void onPanelCollapsed(View view) {
                //  textView.setText("panel Collapse");
            }

            @SuppressLint("SetTextI18n")
            public void onPanelExpanded(View view) {
                //textView.setText("panel expand");
            }


            @SuppressLint("SetTextI18n")
            public void onPanelAnchored(View view) {
                //  textView.setText("panel anchored");
            }


            @SuppressLint("SetTextI18n")
            public void onPanelHidden(View view) {
                //textView.setText("panel is Hidden");
            }
        };
    }
}

