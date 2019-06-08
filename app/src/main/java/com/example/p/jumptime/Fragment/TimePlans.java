package com.example.p.jumptime.Fragment;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.p.jumptime.Controller.DataBase;
import com.example.p.jumptime.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/*
* Планы без привязки к времени на неделю, месяц, год
*
* */
public class TimePlans extends Fragment {

    View rootView;
    // в этих листах храняться данные о каждом плане пользователя на неделю, месяц, год
    ArrayList<String> arWeek;
    ArrayList<String> arMonth;
    ArrayList<String> arYear;

    // в этих листах хранятья индексы, тех дел читаемых из бд для их удаления и редактирования
    ArrayList<Long> indexWeek;
    ArrayList<Long> indexMonth;
    ArrayList<Long> indexYear;


    ListView week;
    ListView month;
    ListView year;

    String title = "Редактирование";
    String message = "Выбери нужное действие";
    String button1String = "Редактировать";
    String button2String = "Выполнено!";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_tasks_for_user_plan, container, false);

        week = rootView.findViewById(R.id.recycler_week);
        month = rootView.findViewById(R.id.recycler_month);
        year = rootView.findViewById(R.id.recycler_year);
        ImageView v1 = rootView.findViewById(R.id.imageView24);
        ImageView v2 = rootView.findViewById(R.id.imageView5);
        ImageView v3 = rootView.findViewById(R.id.imageView26);


        ListSetItemClickListener();


        arWeek = new ArrayList();
        arMonth = new ArrayList();
        arYear = new ArrayList();
        indexWeek = new ArrayList();
        indexMonth = new ArrayList();
        indexYear = new ArrayList();
        readDataFromSQLite();


        v1.setOnClickListener(new View.OnClickListener() {
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
                        if (ed_in_alertDialog.getText().toString().compareTo("") == 0) {
                            Toast.makeText(getContext(), "Введите задачу", Toast.LENGTH_SHORT).show();
                        } else {
                            indexWeek.add(saveInDataBase("week", ed_in_alertDialog.getText().toString()));

                            arWeek.add(ed_in_alertDialog.getText().toString());
                            updateUI();
                            show.dismiss();
                        }
                    }
                });

            }
        });
        v2.setOnClickListener(new View.OnClickListener() {
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
                        if (ed_in_alertDialog.getText().toString().compareTo("") == 0) {
                            Toast.makeText(getContext(), "Введите задачу", Toast.LENGTH_SHORT).show();
                        } else {
                            indexMonth.add(saveInDataBase("month", ed_in_alertDialog.getText().toString()));
                            arMonth.add(ed_in_alertDialog.getText().toString());
                            updateUI();
                            show.dismiss();
                        }
                    }
                });
            }
        });
        v3.setOnClickListener(new View.OnClickListener() {
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
                        if (ed_in_alertDialog.getText().toString().compareTo("") == 0) {
                            Toast.makeText(getContext(), "Введите задачу", Toast.LENGTH_SHORT).show();
                        } else {
                            indexYear.add(saveInDataBase("year", ed_in_alertDialog.getText().toString()));
                            arYear.add(ed_in_alertDialog.getText().toString());
                            updateUI();
                            show.dismiss();
                        }
                    }
                });
            }
        });
        inflater.inflate(R.layout.fragment_calendar_view, container, false);
        return rootView;
    }

    public void updateUI() {
        if (getActivity() != null) {

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.list_text_view, arWeek);
            week.setAdapter(adapter);
            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(), R.layout.list_text_view, arMonth);
            month.setAdapter(adapter1);
            ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getActivity(), R.layout.list_text_view, arYear);
            year.setAdapter(adapter2);
        }
    }

    private void DeleteIndexFromSQLite(long index) {


        DataBase.DBHelper dbHelper = new DataBase.DBHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int delCount = db.delete("table_plans", "id = " + index, null);

        updateUI();


    }

    private void ListSetItemClickListener() {
        week.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
                ad.setTitle(title);  // заголовок
                ad.setMessage(message); // сообщение
                ad.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {

                        //происходит редактирование
                        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
                        final View uview = View.inflate(getContext(), R.layout.dialog_new_plan_task, null);
                        builder.setView(uview);
                        final android.support.v7.app.AlertDialog show = builder.show();

                        Button ok = uview.findViewById(R.id.button6);
                        final EditText ed_in_alertDialog = uview.findViewById(R.id.editText3);
                        ed_in_alertDialog.setText(arWeek.get(i));

                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // происходит удаление из бд
                                if (ed_in_alertDialog.getText().toString().compareTo("") == 0) {
                                    Toast.makeText(getContext(), "Введите задачу", Toast.LENGTH_SHORT).show();
                                } else {
                                    try {
                                        DeleteIndexFromSQLite(indexWeek.get(i));
                                    } catch (IndexOutOfBoundsException e) {
                                    }

                                    arWeek.remove(i);
                                    indexWeek.remove(i);

                                    saveInDataBase("week", ed_in_alertDialog.getText().toString());

                                    arWeek.add(ed_in_alertDialog.getText().toString());
                                    updateUI();
                                    show.dismiss();
                                }
                            }
                        });
                    }
                });
                ad.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        // происходит удаление из бд

                        try {
                            DeleteIndexFromSQLite(indexWeek.get(i));
                        } catch (IndexOutOfBoundsException e) {
                        }
                        arWeek.remove(i);
                        indexWeek.remove(i);

                    }
                });
                ad.setCancelable(true);
                ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        //пользователь ничего не выбрал
                    }
                });
                ad.show();
            }
        });

        month.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
                ad.setTitle(title);  // заголовок
                ad.setMessage(message); // сообщение
                ad.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        //происходит редактирование
                        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
                        final View uview = View.inflate(getContext(), R.layout.dialog_new_plan_task, null);
                        builder.setView(uview);
                        final android.support.v7.app.AlertDialog show = builder.show();

                        Button ok = uview.findViewById(R.id.button6);
                        final EditText ed_in_alertDialog = uview.findViewById(R.id.editText3);
                        ed_in_alertDialog.setText(arMonth.get(i));
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // происходит удаление из бд
                                if (ed_in_alertDialog.getText().toString().compareTo("") == 0) {
                                    Toast.makeText(getContext(), "Введите задачу", Toast.LENGTH_SHORT).show();
                                } else {
                                    try {
                                        DeleteIndexFromSQLite(indexMonth.get(i));
                                    } catch (IndexOutOfBoundsException e) {
                                    }
                                    arMonth.remove(i);
                                    indexMonth.remove(i);
                                    saveInDataBase("month", ed_in_alertDialog.getText().toString());

                                    arMonth.add(ed_in_alertDialog.getText().toString());

                                    updateUI();
                                    show.dismiss();
                                }
                            }
                        });
                    }
                });
                ad.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {

                        // происходит удаление из бд
                        try {
                            DeleteIndexFromSQLite(indexMonth.get(i));
                        } catch (IndexOutOfBoundsException e) {
                        }
                        arMonth.remove(i);
                        updateUI();
                    }
                });
                ad.setCancelable(true);
                ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        //пользователь ничего не выбрал
                    }
                });
                ad.show();
            }
        });

        year.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
                ad.setTitle(title);  // заголовок
                ad.setMessage(message); // сообщение
                ad.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        //происходит редактирование
                        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
                        final View uview = View.inflate(getContext(), R.layout.dialog_new_plan_task, null);
                        builder.setView(uview);
                        final android.support.v7.app.AlertDialog show = builder.show();

                        Button ok = uview.findViewById(R.id.button6);
                        final EditText ed_in_alertDialog = uview.findViewById(R.id.editText3);
                        ed_in_alertDialog.setText(arYear.get(i));
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // происходит удаление из бд
                                if (ed_in_alertDialog.getText().toString().compareTo("") == 0) {
                                    Toast.makeText(getContext(), "Введите задачу", Toast.LENGTH_SHORT).show();
                                } else {
                                    try {
                                        DeleteIndexFromSQLite(indexYear.get(i));
                                    } catch (IndexOutOfBoundsException e) {
                                    }
                                    arYear.remove(i);
                                    indexYear.remove(i);
                                    saveInDataBase("year", ed_in_alertDialog.getText().toString());

                                    arYear.add(ed_in_alertDialog.getText().toString());
                                    updateUI();

                                    show.dismiss();
                                }
                            }
                        });
                    }
                });
                ad.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        final int position = adapterView.getSelectedItemPosition();
                        // происходит удаление из бд
                        try {
                            DeleteIndexFromSQLite(indexYear.get(i));
                        } catch (IndexOutOfBoundsException e) {
                        }
                        arYear.remove(i);
                    }
                });
                ad.setCancelable(true);
                ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        //пользователь ничего не выбрал
                    }
                });
                ad.show();
            }
        });
    }

    private void readDataFromSQLite() {
        final DataBase.DBHelper dbHelper;

        // создаем объект для данных
        ContentValues cv = new ContentValues();
        // создаем объект для создания и управления версиями БД
        dbHelper = new DataBase.DBHelper(getContext());
        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // делаем запрос всех данных из таблицы mytable, получаем Cursor
        Cursor c = db.query("table_plans", null, null, null, null, null, null);

        // ставим позицию курсора на первую строку выборки если в выборке нет строк, вернется false

        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex("name");
            int dataColIndex = c.getColumnIndex("time");
            int categoryColIndex = c.getColumnIndex("category");
            int kColIndex = c.getColumnIndex("active");


            do {

                if (c.getString(categoryColIndex).compareTo("week") == 0) {
                    arWeek.add(c.getString(nameColIndex));
                    indexWeek.add(c.getLong(idColIndex));
                } else if (c.getString(categoryColIndex).compareTo("month") == 0) {
                    arMonth.add(c.getString(nameColIndex));
                    indexMonth.add(c.getLong(idColIndex));
                } else if (c.getString(categoryColIndex).compareTo("year") == 0) {
                    arYear.add(c.getString(nameColIndex));
                    indexYear.add(c.getLong(idColIndex));
                }

                // переход на следующую строку, а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        } else
            Log.d("Tah", "0 rows");

        updateUI();
    }

    private long saveInDataBase(String category, String resourse_name) {
        // создаем объект для создания и управления версиями БД
        DataBase.DBHelper dbHelper = new DataBase.DBHelper(getContext());
        ContentValues cvq = new ContentValues();
        // подключаемся к БД
        SQLiteDatabase dbq = dbHelper.getWritableDatabase();
        String dataBegin;
        // Текущее время
        Date currentDate = new Date();
        // Форматирование времени как "день.месяц.год"
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        dataBegin = dateFormat.format(currentDate);
        cvq.put("category", category);
        cvq.put("name", resourse_name);
        cvq.put("time", dataBegin);
        cvq.put("active", "1");
        // вставляем запись и получаем ее ID
        long rowID = dbq.insert("table_plans", null, cvq);

        // Toast.makeText(getContext(), "row inserted, ID = " + rowID, Toast.LENGTH_SHORT).show();

        return rowID;
    }
}
