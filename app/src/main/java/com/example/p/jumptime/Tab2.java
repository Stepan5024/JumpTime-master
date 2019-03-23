package com.example.p.jumptime;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Tab2 extends Fragment {
    ArrayList<TaskForRecyclerView> tasks;
    FirebaseUser user;

    DataAdapter adapter;
    ArrayList sizeArray = new ArrayList();
    Button add;
    View rootView;
    List<String[]> array;
    ArrayList images;
    RecyclerView recyclerView;
    CoordinatorLayout coordinatorLayout;
    DatabaseReference myRef;
    DatabaseReference myRef1;
    LinearLayoutManager layoutManager;

    public Tab2() {
        tasks = new ArrayList<>();
        array = new ArrayList<>();
        images = new ArrayList();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.tab2, container, false);
        user = FirebaseAuth.getInstance().getCurrentUser();

        myRef = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
        myRef1 = FirebaseDatabase.getInstance().getReference();
        coordinatorLayout = rootView.findViewById(R.id.coordinatorLayout);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DataAdapter(getContext(), tasks);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
        setElement();
        setRef();
        enableSwipeToDeleteAndUndo();



        return rootView;

    }
    private void setRef(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSbapshot : dataSnapshot.getChildren()){
                    images.add((childSbapshot.getValue()));
                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (int i = 0; i < sizeArray.size(); i++) {
                    String s = sizeArray.get(i).toString();

                    // Toast.makeText(rootView.getContext(),s, Toast.LENGTH_SHORT).show();
                    String name = "" + dataSnapshot.child("users").child(user.getUid()).child("tasks").child(s).child("name").getValue(String.class);
                    String data = ""+ dataSnapshot.child("users").child(user.getUid()).child("tasks").child(s).child("data").getValue(String.class);
                    String kilo = ""+ dataSnapshot.child("users").child(user.getUid()).child("tasks").child(s).child("kilo").getValue(String.class);
                    String time = ""+ dataSnapshot.child("users").child(user.getUid()).child("tasks").child(s).child("time").getValue(String.class);
                    String project = ""+ dataSnapshot.child("users").child(user.getUid()).child("tasks").child(s).child("project").getValue(String.class);

                    String[] a = {name,data,kilo,time,project};
                    array.add(a);

                    tasks.add(new TaskForRecyclerView(s, a[1],a[3], R.mipmap.ic_launcher, getActivity()));
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    private void setElement(){

        DatabaseReference info = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("tasks");
        info.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String child = ds.getKey();
                    sizeArray.add(child);
                    Log.d("TAG", child);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("w", "onCancelled", databaseError.toException());
            }
        });


    }
    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                final String item = String.valueOf(adapter.getData().get(position));

                adapter.removeItem(position);


                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        adapter.restoreItem(tasks.get(0), position);
                        recyclerView.scrollToPosition(position);
                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }


}