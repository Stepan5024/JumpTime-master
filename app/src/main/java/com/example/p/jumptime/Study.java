package com.example.p.jumptime;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Study extends Fragment {

    List<FastItemForRecycler> item = new ArrayList<>();
    public static EditText ed;
    ArrayList<String> tasks = new ArrayList<String>();
    ArrayList<Integer> indexTasks = new ArrayList<Integer>();
    ListView list;
    String title = "Редактирование";
    String message = "Выбери нужное действие";
    String button1String = "Редактировать";
    String button2String = "Выполнено!";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_study, container, false);
        ed = view.findViewById(R.id.task_plan);
        list = view.findViewById(R.id.listTask);

       // readDataFromSQLite();

        Button butAdd = view.findViewById(R.id.button7);
        butAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ed.getText().toString().compareTo("") == 0) {
                } else {

                    tasks.add(ed.getText().toString());

                    saveInDataBase("Самообразование", ed.getText().toString());
                    updateUI();
                    ed.setText("");
                }
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                        ed_in_alertDialog.setText(tasks.get(i));

                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // происходит удаление из бд

                              try{  DeleteIndexFromSQLite((Integer) indexTasks.get(i));}
                              catch(IndexOutOfBoundsException e ){}
                                tasks.remove(i);
                                indexTasks.remove(i);

                                saveInDataBase("Самообразование", ed_in_alertDialog.getText().toString());

                                tasks.add(ed_in_alertDialog.getText().toString());
                                updateUI();
                                show.dismiss();
                            }
                        });
                    }
                });
                ad.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        // происходит удаление из бд

                      try{  DeleteIndexFromSQLite((Integer) indexTasks.get(i));}
                      catch (IndexOutOfBoundsException e){}
                        tasks.remove(i);
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        setInitialData();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(layoutManager);
        // создаем адаптер
        DataAdapterStudy adapter = new DataAdapterStudy(getContext(), item);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
        return view;

    }

    private void DeleteIndexFromSQLite(int index) {


        DataBase.DBHelper dbHelper = new DataBase.DBHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int delCount = db.delete("table_plans", "id = " + index, null);

        updateUI();


    }

    private void setInitialData() {

        item.add(new FastItemForRecycler("Чтение ", R.drawable.read, getActivity()));
        item.add(new FastItemForRecycler("Обдумать ", R.drawable.mozg, getActivity()));
        item.add(new FastItemForRecycler("Новая тема ", R.drawable.newtema, getActivity()));
        item.add(new FastItemForRecycler("Анализ ", R.drawable.analiz, getActivity()));
        item.add(new FastItemForRecycler("Личное ", R.drawable.lich, getActivity()));


    }

    public void updateUI() {
        if (getActivity() != null) {

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.list_text_view, tasks);
            list.setAdapter(adapter);

        }
    }

    private void readDataFromSQLite() {
        final DataBase.DBHelper dbHelper;

        // создаем объект для данных
        ContentValues cv = new ContentValues();
        // создаем объект для создания и управления версиями БД
        dbHelper = new DataBase.DBHelper(getContext());
        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // делаем запрос всех данных из таблицы, получаем Cursor
        Cursor c = db.query("table_plans", null, null, null, null, null, null);

        // ставим позицию курсора на первую строку выборки если в выборке нет строк, вернется false

        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex("category");
            int dataColIndex = c.getColumnIndex("name");
            int categoryColIndex = c.getColumnIndex("time");
            int kColIndex = c.getColumnIndex("active");


            do {
                if (c.getString(nameColIndex).compareTo("Самообразование") == 0) {
                    //     Toast.makeText(getContext(),"! "+ c.getString(dataColIndex), Toast.LENGTH_SHORT).show();
                    tasks.add(c.getString(dataColIndex));
                    indexTasks.add(c.getInt(idColIndex));
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



        return rowID;
    }
    class DataAdapterStudy extends RecyclerView.Adapter<DataAdapterStudy.ViewHolder> {

        private LayoutInflater inflater;
        private List<FastItemForRecycler> arr;
        public  String activeString = "";

        DataAdapterStudy(Context context, List<FastItemForRecycler> list) {
            this.arr = list;
            this.inflater = LayoutInflater.from(context);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @NonNull
        public DataAdapterStudy.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.recycler_viewtest, parent, false);
            return new DataAdapterStudy.ViewHolder(view);
        }


        public void onBindViewHolder(@NonNull final DataAdapterStudy.ViewHolder viewHolder, final int position) {
            final FastItemForRecycler new1 = arr.get(position);
            viewHolder.imageView.setImageResource(new1.getImage());
            viewHolder.newsView.setText(new1.getName());

            // обработчик нажатия
            viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {
                    activeString = new1.getName();
                    Study.ed.setText(activeString);
                }
            });
        }

        @Override
        public int getItemCount() {
            return arr.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            final ImageView imageView;
            final TextView newsView;
            LinearLayout linearLayout;

            ViewHolder(View view) {
                super(view);
                imageView = (ImageView) view.findViewById(R.id.image);
                newsView = (TextView) view.findViewById(R.id.news);
                linearLayout = (LinearLayout) view.findViewById((R.id.linLayout));
            }
        }
    }
}
