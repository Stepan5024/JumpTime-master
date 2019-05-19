package com.example.p.jumptime;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;


public class AddTask extends Fragment {
    Calendar dateAndTime = Calendar.getInstance();
    TextView priority;
    TextView reminder;
    View rootView;

    String timeTask = "10:00"; // потом инициализировать текущим временем
    Button add;
    TextView EndDateTime;
    EditText ValueView;
    Spinner spinner_remind;
    Spinner spinner_priority;
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

        reminder = rootView.findViewById(R.id.remind);
        EndDateTime = rootView.findViewById(R.id.time);
        ValueView = rootView.findViewById(R.id.ValueView);
        // Текущее время
        Date currentDate = new Date();
        // Форматирование времени как "день.месяц.год"
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

        dataTask = dateFormat.format(currentDate);
        spinner_remind = (Spinner) rootView.findViewById(R.id.spinner_remind);
        spinner_priority = (Spinner) rootView.findViewById(R.id.spinner_priority);

        spinner_remind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                String[] choose = getResources().getStringArray(R.array.list_for_remind);
                selected_remind = choose[selectedItemPosition];

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


            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


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
                Fragment fragment = new TasksForCurrentPerfomance();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();




            }
        });



        EndDateTime = rootView.findViewById(R.id.time);
        EndDateTime.setText(DateUtils.formatDateTime(getContext(),
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_TIME));



        EndDateTime.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                new TimePickerDialog(getContext(), t,
                        dateAndTime.get(Calendar.HOUR_OF_DAY),
                        dateAndTime.get(Calendar.MINUTE), true)
                        .show();

                new DatePickerDialog(Objects.requireNonNull(getContext()), d,
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

                    dataTask = dayOfMonth + "." + (monthOfYear+1) + "." + year;
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