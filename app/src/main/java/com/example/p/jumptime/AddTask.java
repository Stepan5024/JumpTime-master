package com.example.p.jumptime;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;


public class AddTask extends Fragment {
    Calendar dateAndTime = Calendar.getInstance();
    TextView priority;
    TextView repeat;
    TextView reminder;
    TextView category;
    View rootView;

    String timeTask = "10:00"; // потом инициализировать текущим временем
    Button add;
    TextView EndDateTime;
    EditText ValueView;
    Spinner spinner_remind;
    Spinner spinner_priority;
    Spinner spinner_repeat;
    String selected_repeat;
    String selected_remind;
    int selected_priority = 0;
    final String LOG_TAG = "myLogs";
    String dataTask;

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
        EndDateTime = rootView.findViewById(R.id.time);
        ValueView = rootView.findViewById(R.id.ValueView);
        spinner_remind = (Spinner) rootView.findViewById(R.id.spinner_remind);
        spinner_priority = (Spinner) rootView.findViewById(R.id.spinner_priority);
        spinner_repeat = (Spinner) rootView.findViewById(R.id.spinner_repeat);
        spinner_remind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                String[] choose = getResources().getStringArray(R.array.list_for_remind);
                selected_remind = choose[selectedItemPosition];
                Toast toast = Toast.makeText(getContext(),
                        "Ваш выбор: " + choose[selectedItemPosition], Toast.LENGTH_SHORT);
                toast.show();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinner_priority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                String[] choose = getResources().getStringArray(R.array.list_for_priority);
                if(choose[selectedItemPosition].compareTo("Не важно") == 0){
                    selected_priority = 0;
                }
                else if(choose[selectedItemPosition].compareTo("Среднее") == 0) {
                    selected_priority = 1;
                }
                else if(choose[selectedItemPosition].compareTo("Важно") == 0) {
                    selected_priority = 2;
                }
                else {
                    selected_priority = 3;
                }

                Toast toast = Toast.makeText(getContext(),
                        "Ваш выбор: " + choose[selectedItemPosition], Toast.LENGTH_SHORT);

                toast.show();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinner_repeat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                String[] choose = getResources().getStringArray(R.array.list_for_repeat);
                selected_repeat = choose[selectedItemPosition];
                Toast toast = Toast.makeText(getContext(),
                        "Ваш выбор: " + choose[selectedItemPosition], Toast.LENGTH_SHORT);
                toast.show();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        dataTask = EndDateTime.getText().toString();
                    /*final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
	                        final View uview = View.inflate(getContext(), R.layout.dialog_field_kilo, null);
	                        builder.setView(uview);
	                        final AlertDialog show = builder.show();

	                        Button ok = uview.findViewById(R.id.good_day);
	                        ok.setOnClickListener(new View.OnClickListener() {
	                            @Override
	                            public void onClick(View view) {


	                                show.dismiss();
	                            }
	                        });*/

        add = (Button) rootView.findViewById(R.id.addBut);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (ValueView.getText().toString().compareTo("")!=0) {
                          /*  myRef.child("users").child(user.getUid()).child("tasks").child(ValueView.getText().toString()).child("name").setValue(ValueView.getText().toString());
                            myRef.child("users").child(user.getUid()).child("tasks").child(ValueView.getText().toString()).child("data").setValue(dataTask);
                            myRef.child("users").child(user.getUid()).child("tasks").child(ValueView.getText().toString()).child("time").setValue(timeTask);
                            myRef.child("users").child(user.getUid()).child("tasks").child(ValueView.getText().toString()).child("priority").setValue(selected_priority+"");
                            myRef.child("users").child(user.getUid()).child("tasks").child(ValueView.getText().toString()).child("project").setValue("#1");
*/
                    DataBase.DBHelper dbHelper = new DataBase.DBHelper(getContext());
                    // подключаемся к БД

                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    // создаем объект для данных

                    ContentValues cv = new ContentValues();
                    Log.d(LOG_TAG, "--- Insert in mytable: ---");
                    // подготовим данные для вставки в виде пар: наименование столбца -
                    // значение
                    cv.put("name", ValueView.getText().toString());
                    cv.put("data", dataTask);
                    cv.put("time",timeTask);
                    cv.put("k", "k");
                    cv.put("i", "i");
                    cv.put("l", "l");
                    cv.put("o", "o");
                    cv.put("priority", selected_priority);
                    cv.put("project", "#1");
                    cv.put("active", "1");
                    // вставляем запись и получаем ее ID
                    long rowID = db.insert("mytable", null, cv);
                    Log.d(LOG_TAG, "row inserted, ID = " + rowID);
                }
                ValueView.setText("");
                // очистить введенные данные потом создав метод инитиализе





            }
        });



        EndDateTime = rootView.findViewById(R.id.time);
        EndDateTime.setText(DateUtils.formatDateTime(getContext(),
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_TIME));



        EndDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(getContext(), t,
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

            DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    dateAndTime.set(Calendar.YEAR, year);
                    dateAndTime.set(Calendar.MONTH, monthOfYear);
                    dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    Toast.makeText(getContext(),
                            "year  = " + year + "month " + monthOfYear + " day " + dayOfMonth, Toast.LENGTH_SHORT).show();
                    dataTask = dayOfMonth + "." + monthOfYear + "." + year;
                    setInitialDateTime();
                }
            };
            TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    dateAndTime.set(Calendar.MINUTE, minute);
                    timeTask = hourOfDay + ":" + minute;
                    setInitialDateTime();
                }
            };
        });

        return rootView;

    }


}