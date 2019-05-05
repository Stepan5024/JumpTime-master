package com.example.p.jumptime;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class KILO extends Fragment {

    Button add;
    String k;
    String i;
    String l;
    String o;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_kilo, container, false);
        ImageView help = view.findViewById(R.id.helpAdmin);
        help.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
                builder.setTitle("Что такое КИЛО?")
                        .setMessage("Для того, чтобы мечты сбывались, их необходимо перевести в форму целей, " +
                                "и упорно трудиться на пути к их достижению." +
                                "Цель – . Либо вы выбираете цели, либо цели других выбирают вас. За свою практику, мне удалось насобирать разные методы достижения целей, они разные по произношению, но очень близки по смыслу.")
                        .setCancelable(false)
                        .setNegativeButton("Ок, закрыть",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        final TextView tv_k = view.findViewById(R.id.goal_k);
        final TextView tv_i = view.findViewById(R.id.goal_i);
        final TextView tv_l = view.findViewById(R.id.goal_l);
        final TextView tv_o = view.findViewById(R.id.goal_o);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference myRef;
        FirebaseDatabase database = FirebaseDatabase.getInstance();


        if (user != null) {
            myRef = database.getReference();


            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    k = dataSnapshot.child("users").child(user.getUid()).child("infoUser").child("k").getValue(String.class);
                    i = dataSnapshot.child("users").child(user.getUid()).child("infoUser").child("i").getValue(String.class);
                    l = dataSnapshot.child("users").child(user.getUid()).child("infoUser").child("l").getValue(String.class);
                    o = dataSnapshot.child("users").child(user.getUid()).child("infoUser").child("dataEnd").getValue(String.class);

                    tv_k.setText(k);
                    tv_i.setText(i);
                    tv_l.setText(l);
                    tv_o.setText(o);
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