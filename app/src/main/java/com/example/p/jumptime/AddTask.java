package com.example.p.jumptime;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Calendar;


public class AddTask extends Fragment {
    Calendar dateAndTime=Calendar.getInstance();
    TextView priority;
    TextView repeat;
    TextView reminder;
    TextView category;
    View rootView;
    Button add;
    TextView EndDateTime;
    TextView StartDateTime;
    EditText ValueView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_add_task, container, false);
        priority = rootView.findViewById(R.id.priority);
        repeat = rootView.findViewById(R.id.repeat);
        category = rootView.findViewById(R.id.category);
        reminder = rootView.findViewById(R.id.remind);

        ValueView = rootView.findViewById(R.id.ValueView);


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


        StartDateTime = rootView.findViewById(R.id.date);
        EndDateTime = rootView.findViewById(R.id.time);
        EndDateTime.setText(DateUtils.formatDateTime(getContext(),
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_TIME));
        StartDateTime.setText(DateUtils.formatDateTime(getContext(),
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_TIME));

        StartDateTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new TimePickerDialog(getContext(),t,
                        dateAndTime.get(Calendar.HOUR_OF_DAY),
                        dateAndTime.get(Calendar.MINUTE), true)
                        .show();
                new DatePickerDialog(getContext(), d,
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH))
                        .show();

            }

            private void setInitialDateTime() {

                StartDateTime.setText(DateUtils.formatDateTime(getContext(),
                        dateAndTime.getTimeInMillis(),
                        DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_TIME));
            }
            DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    dateAndTime.set(Calendar.YEAR, year);
                    dateAndTime.set(Calendar.MONTH, monthOfYear);
                    dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    setInitialDateTime();
                }
            };

            TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    dateAndTime.set(Calendar.MINUTE, minute);
                    setInitialDateTime();
                }
            };
        });
        EndDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(getContext(),t,
                        dateAndTime.get(Calendar.HOUR_OF_DAY),
                        dateAndTime.get(Calendar.MINUTE), true)
                        .show();
                new DatePickerDialog(getContext(), d,
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH))
                        .show();


            }

            private void setInitialDateTime() {

                EndDateTime.setText(DateUtils.formatDateTime(getContext(),
                        dateAndTime.getTimeInMillis(),
                        DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_TIME));
            }

            DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    dateAndTime.set(Calendar.YEAR, year);
                    dateAndTime.set(Calendar.MONTH, monthOfYear);
                    dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    setInitialDateTime();
                }
            };
            TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    dateAndTime.set(Calendar.MINUTE, minute);
                    setInitialDateTime();
                }
            };
        });

        return rootView;

    }


}