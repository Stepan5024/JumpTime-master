package com.example.p.jumptime;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Step extends Fragment {

    DataAdapterStep adapter;
    public static ArrayList<TaskForStep> tasks;
    LinearLayoutManager layoutManager;
    RecyclerView rec;
    FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step, container, false);
        rec = view.findViewById(R.id.recycler_step);
        Button button = view.findViewById(R.id.button9);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
                final View uview = View.inflate(getContext(), R.layout.alert_dialog_new_plan_task, null);
                builder.setView(uview);
                final android.support.v7.app.AlertDialog show = builder.show();
                final String[] date = new String[1];
                Date currentDate = new Date();
                // Форматирование времени как "день.месяц.год"
                DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

                date[0] = dateFormat.format(currentDate);
                DatePicker mDatePicker = (DatePicker) uview.findViewById(R.id.datePicker);
                Calendar today = Calendar.getInstance();
                mDatePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
                        today.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

                            @Override
                            public void onDateChanged(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                date[0] = dayOfMonth + "." + (monthOfYear + 1) + "." + year;

                            }
                        });
                Button ok = uview.findViewById(R.id.button6);
                final EditText ed_in_alertDialog = uview.findViewById(R.id.editText3);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        tasks.add(new TaskForStep(ed_in_alertDialog.getText().toString(), date[0], 1, getActivity()));
                        updateUI();
                        if (ed_in_alertDialog.getText().toString().compareTo("") == 0 && date[0].compareTo("") == 0) {
                            Toast.makeText(getContext(), "Введите название и дату", Toast.LENGTH_SHORT).show();
                        } else show.dismiss();

                    }
                });
            }
        });
        tasks = new ArrayList();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        adapter = new DataAdapterStep(getContext(), tasks);
        rec.setAdapter(adapter);

        rec.setLayoutManager(layoutManager);
        return view;

    }

    public void updateUI() {
        if (getActivity() != null) {

            DataAdapterStep adapter = new DataAdapterStep(getContext(), tasks);
            rec.setAdapter(adapter);

        }
    }

    class DataAdapterStep extends RecyclerView.Adapter<com.example.p.jumptime.DataAdapterStep.ViewHolder> {

        private LayoutInflater inflater;
        private ArrayList<TaskForStep> news;
        View view;

        class ViewHolder extends RecyclerView.ViewHolder {


            final TextView ViewName;
            final TextView ViewTime;
            LinearLayout linearLayout;

            ViewHolder(View view) {
                super(view);


                ViewName = view.findViewById(R.id.DataTask);
                ViewTime = view.findViewById(R.id.TimeTask);
                linearLayout = view.findViewById((R.id.linLayout));
            }
        }

        DataAdapterStep(Context context, ArrayList<TaskForStep> phones) {
            this.news = phones;
            this.inflater = LayoutInflater.from(context);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @NonNull
        public com.example.p.jumptime.DataAdapterStep.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            view = inflater.inflate(R.layout.recycler_view_step, parent, false);
            return new com.example.p.jumptime.DataAdapterStep.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final com.example.p.jumptime.DataAdapterStep.ViewHolder viewHolder, final int position) {
            final TaskForStep new1 = news.get(position);
            viewHolder.ViewTime.setText(new1.getTaskData());
            viewHolder.ViewName.setText(new1.getTaskName());


            // обработчик нажатия
            viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {


                }
            });
        }

        public void restoreItem(TaskForStep task, int position) {
            news.add(position, task);
            notifyItemInserted(position);
        }

        public void removeItem(int position) {
            news.remove(position);
            notifyItemRemoved(position);
        }

        public List<TaskForStep> getData() {
            return news;
        }

        @Override
        public int getItemCount() {
            return news.size();
        }

        private void DeleteIndexFromSQLite(int index) {


            DataBase.DBHelper dbHelper = new DataBase.DBHelper(getContext());
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            int delCount = db.delete("table_steps", "id = " + index, null);
            updateUI();


        }

    }


}
