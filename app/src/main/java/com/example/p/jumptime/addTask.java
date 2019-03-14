package com.example.p.jumptime;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;


public class addTask extends Fragment {
    String task;
    String data;
    List Kilo = new ArrayList<String>();
    String priority;
    boolean reminder;
    String addTo;

    View rootView;
    Button add;
    EditText ValueView;
    String[] listfield = {"Заметки", "Срок", "Напоминание", "Приоритет", "Список"};
    ListView mList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_add_task, container, false);
        ValueView = rootView.findViewById(R.id.ValueView);
        mList = (ListView) rootView.findViewById(R.id.Field);
        updateUI();
        add = (Button) rootView.findViewById(R.id.addBut);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String email = user.getEmail();

                        myRef.child("users").child(user.getUid()).child("tasks").child(ValueView.getText().toString()).child("data").setValue("13/03/2018 12:00");
                        myRef.child("users").child(user.getUid()).child("tasks").child(ValueView.getText().toString()).child("priority").setValue("0");
                        myRef.child("users").child(user.getUid()).child("tasks").child(ValueView.getText().toString()).child("kilo").setValue("kilo");
                        myRef.child("users").child(user.getUid()).child("tasks").child(ValueView.getText().toString()).child("project").setValue("#1");
                        myRef.child("users").child(user.getUid()).child("tasks").child(ValueView.getText().toString()).child("active").setValue(1);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });


        return rootView;

    }

    public void updateUI() {
        if (getActivity() != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(rootView.getContext(), R.layout.list_text_view, listfield);
            mList.setAdapter(adapter);
        }
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // по позиции получаем выбранный элемент
                String selectedItem = listfield[position];

                switch (selectedItem) {
                    case "Заметки":
                        Toast.makeText(rootView.getContext(), selectedItem, Toast.LENGTH_SHORT).show();
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        final View uview = View.inflate(getContext(), R.layout.dialog_field_kilo, null);
                        builder.setView(uview);
                        final AlertDialog show = builder.show();

                        Button ok = uview.findViewById(R.id.good_day);
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {


                                show.dismiss();
                            }
                        });

                        break;
                    case "Срок":
                        Toast.makeText(rootView.getContext(), selectedItem, Toast.LENGTH_SHORT).show();
                        final AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
                        final View uview2 = View.inflate(getContext(), R.layout.dialog_calendar, null);
                        builder2.setView(uview2);
                        final AlertDialog show2 = builder2.show();
                        Button ok2 = uview2.findViewById(R.id.good_day);
                        ok2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                show2.dismiss();
                            }
                        });
                        break;
                    case "Напоминание":
                        Toast.makeText(rootView.getContext(), selectedItem, Toast.LENGTH_SHORT).show();
                        break;
                    case "Приоритет":
                        Toast.makeText(rootView.getContext(), selectedItem, Toast.LENGTH_SHORT).show();
                        break;
                    case "Список":
                        Toast.makeText(rootView.getContext(), selectedItem, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(rootView.getContext(), "default", Toast.LENGTH_SHORT).show();
                }
                // установка текста элемента TextView
                //  selection.setText(selectedItem);
            }
        });
    }

}