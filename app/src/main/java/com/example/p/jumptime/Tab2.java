package com.example.p.jumptime;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Tab2 extends Fragment {
    List<TaskForRecyclerView> tasks = new ArrayList<>();
    Button add;
    View rootView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.tab2, container, false);
        setInitialData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        recyclerView.setLayoutManager(layoutManager);
        // создаем адаптер
        DataAdapter adapter = new DataAdapter(getContext(), tasks);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);






        return rootView;

    }
    private void setInitialData(){
      FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        /*  DatabaseReference zonesRef = FirebaseDatabase.getInstance().getReference("users");
        DatabaseReference zone1Ref = zonesRef.child(user.getUid());
        DatabaseReference zone1NameRef3 = zone1Ref.child("tasks");
        DatabaseReference zone1NameRef = zone1NameRef3.child("data");
        zone1Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("TAGGGGTAG", dataSnapshot.child("ZNAME").getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TAGGGGTAG", "onCancelled", databaseError.toException());
            }
        });*/
      /*  FirebaseDatabase.getInstance().getReference()
                .child("articles")
                .child("content")
                .child("ta")
                .child("all")
                .child("KkOz7Zg9iNqms1vZJA6")
                .child("articleTags")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        @SuppressWarnings("unchecked")
                        Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                        String firstValue = (String) map.get("0");
                        String secondValue = (String) map.get("1");
                        String thirdValue = (String) map.get("2");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {/*Do Nothing*/
   /* }
                });*/
        tasks.add(new TaskForRecyclerView ("Какая-то новость", "09-09-19", "18:00",  R.mipmap.ic_launcher,getActivity()));
        tasks.add(new TaskForRecyclerView ("Elite z3", "09-09-19", "18:00", R.mipmap.ic_launcher,getActivity()));
        tasks.add(new TaskForRecyclerView ("Galaxy S8","09-09-19", "18:00", R.mipmap.ic_launcher,getActivity()));
        tasks.add(new TaskForRecyclerView ("LG G 5","09-09-19", "18:00",  R.mipmap.ic_launcher,getActivity()));
        tasks.add(new TaskForRecyclerView ("Elite z3", "09-09-19", "18:00",R.mipmap.ic_launcher,getActivity()));
        tasks.add(new TaskForRecyclerView ("Elite z3", "09-09-19", "18:00", R.mipmap.ic_launcher,getActivity()));
        tasks.add(new TaskForRecyclerView ("Elite z3", "09-09-19", "18:00", R.mipmap.ic_launcher,getActivity()));
    }


}