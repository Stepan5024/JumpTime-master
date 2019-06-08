package com.example.p.jumptime.Fragment;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.p.jumptime.Controller.DataBase;
import com.example.p.jumptime.R;
import com.example.p.jumptime.Model.TaskForStep;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * данный фрагмент используется в TabLayout вкладке "Шаги"
 * считывает данные из БД Firebase  представляет ввиде RecyclerView пользователю, также добавляет новые шаги
 */

public class Step extends Fragment {

    DataAdapterStep adapter;
    public static ArrayList<TaskForStep> tasks;
    LinearLayoutManager layoutManager;
    RecyclerView rec;
    FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser user;
    int counter = 1;

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
        tasks = new ArrayList();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        setElementToStep();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               makeDialog();


            }
        });


        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        adapter = new DataAdapterStep(getContext(), tasks);
        rec.setAdapter(adapter);
        rec.setLayoutManager(layoutManager);

        return view;

    }
    private void makeDialog(){
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
        final View uview = View.inflate(getContext(), R.layout.alert_dialog_new_plan_task, null);
        builder.setView(uview);
        final android.support.v7.app.AlertDialog show = builder.show();
        final String[] date = new String[1];
        Date currentDate = new Date();
        // Форматирование времени как "день.месяц.год"
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        final EditText k = uview.findViewById(R.id.k);
        final EditText i = uview.findViewById(R.id.i);
        final EditText l = uview.findViewById(R.id.l);

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
        final EditText ed_in_alertDialog1 = uview.findViewById(R.id.editText4);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (ed_in_alertDialog.getText().toString().compareTo("") == 0 && date[0].compareTo("") == 0) {
                    Toast.makeText(getContext(), "Введите название и дату", Toast.LENGTH_SHORT).show();
                } else {
                    show.dismiss();
                    tasks.add(new TaskForStep(ed_in_alertDialog.getText().toString(), date[0],k.getText().toString(),i.getText().toString(),l.getText().toString() ,Integer.valueOf(ed_in_alertDialog1.getText().toString()),0, counter, getActivity()));
                    updateUI();


                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            myRef.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child("counter").setValue((counter) + "");
                            myRef.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child("" + (Integer.valueOf(counter) - 1)).child("data").setValue(date[0]);
                            myRef.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child("" + (Integer.valueOf(counter) - 1)).child("name").setValue(ed_in_alertDialog.getText().toString());
                            myRef.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child("" + (Integer.valueOf(counter) - 1)).child("hp").setValue("0");
                            myRef.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child("" + (Integer.valueOf(counter) - 1)).child("HpMax").setValue(ed_in_alertDialog1.getText().toString());
                            myRef.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child("" + (Integer.valueOf(counter) - 1)).child("count").setValue((counter) + "");
                            myRef.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child("" + (Integer.valueOf(counter) - 1)).child("k").setValue(k.getText().toString());
                            myRef.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child("" + (Integer.valueOf(counter) - 1)).child("i").setValue(i.getText().toString());
                            myRef.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child("" + (Integer.valueOf(counter) - 1)).child("l").setValue(l.getText().toString());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    counter++;
                }

            }
        });
    }
    private void setElementToStep() {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    counter = Integer.valueOf(dataSnapshot.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child("counter").getValue(String.class));
                    for (int i = 1; i < counter + 1; i++) {

                        String data = dataSnapshot.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child("" + i).child("data").getValue(String.class);
                        String name = dataSnapshot.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child("" + i).child("name").getValue(String.class);
                        String k = dataSnapshot.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child("" + i).child("k").getValue(String.class);
                        String ki = dataSnapshot.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child("" + i).child("i").getValue(String.class);
                        String l = dataSnapshot.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child("" + i).child("l").getValue(String.class);

                        int HpMax = Integer.valueOf(dataSnapshot.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child("" + i).child("HpMax").getValue(String.class));
                        int hp = Integer.valueOf(dataSnapshot.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child("" + i).child("hp").getValue(String.class));
                        int count = Integer.valueOf(dataSnapshot.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child("" + i).child("count").getValue(String.class));
                        tasks.add(new TaskForStep(name, data,k, ki,l, HpMax, hp, count, getActivity()));
                        updateUI();
                    }
                } catch (NumberFormatException ex) {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void updateUI() {
        if (getActivity() != null) {

            DataAdapterStep adapter = new DataAdapterStep(getContext(), tasks);
            rec.setAdapter(adapter);

        }
    }

    class DataAdapterStep extends RecyclerView.Adapter<com.example.p.jumptime.Controller.DataAdapterStep.ViewHolder> {

        private LayoutInflater inflater;
        private ArrayList<TaskForStep> news;
        View view;
        String message = "Как Помочь Себе Самому";
        String title = "КПСС";
        String button1String = "Редактировать";
        String button2String = "Статистика";
        private int progress = 0;
        private ProgressBar pbHorizontal;
        private TextView tvProgressHorizontal;
        private int mYear, mMonth, mDay;

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
        public com.example.p.jumptime.Controller.DataAdapterStep.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            view = inflater.inflate(R.layout.recycler_view_step, parent, false);
            return new com.example.p.jumptime.Controller.DataAdapterStep.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final com.example.p.jumptime.Controller.DataAdapterStep.ViewHolder viewHolder, final int position) {
            final TaskForStep new1 = news.get(position);
            viewHolder.ViewTime.setText(new1.getTaskData());
            viewHolder.ViewName.setText(new1.getTaskName());


            // обработчик нажатия
            viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
                    ad.setTitle(title);  // заголовок
                    ad.setMessage(message); // сообщение
                    ad.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {

                            //происходит редактирование
                            final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
                            final View uview = View.inflate(getContext(), R.layout.alert_dialog_redact, null);
                            builder.setView(uview);
                            final android.support.v7.app.AlertDialog show = builder.show();
                            final String[] editTextDateParam = new String[1];
                            editTextDateParam[0] = new1.getTaskData();
                            Button ok = uview.findViewById(R.id.button6);
                            final EditText ed_in_alertDialog = uview.findViewById(R.id.editText3);
                            final EditText ed_in_alertDialog2 = uview.findViewById(R.id.editText4);
                            final TextView tv = uview.findViewById(R.id.textView17);
                            tv.setText("Конечная дата  " + new1.getTaskData());
                            tv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // вызываем диалог с выбором даты
                                    // получаем текущую дату
                                    final Calendar cal = Calendar.getInstance();
                                    mYear = cal.get(Calendar.YEAR);
                                    mMonth = cal.get(Calendar.MONTH);
                                    mDay = cal.get(Calendar.DAY_OF_MONTH);

                                    // инициализируем диалог выбора даты текущими значениями
                                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                                            new DatePickerDialog.OnDateSetListener() {
                                                @Override
                                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                                    editTextDateParam[0] = dayOfMonth + "." + (monthOfYear + 1) + "." + year;
                                                    tv.setText("Конечная дата   " + editTextDateParam[0]);
                                                }
                                            }, mYear, mMonth, mDay);
                                    datePickerDialog.show();
                                }
                            });
                            //

                            ed_in_alertDialog.setText(new1.getTaskName());
                            ed_in_alertDialog2.setText(new1.getHpMax() + "");
                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // происходит сохранение
                                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            Toast.makeText(getContext(), ed_in_alertDialog.getText() + "", Toast.LENGTH_LONG).show();


                                            new1.setTaskName(ed_in_alertDialog.getText().toString());
                                            new1.setHpMax(Integer.valueOf(ed_in_alertDialog2.getText().toString()));
                                            new1.setTaskData(editTextDateParam[0]);
                                            String a = editTextDateParam[0];
                                            String b = new1.getName();
                                            String c = new1.getHpMax() + "";
                                            String d = new1.getCounter() + "";
                                            String e = new1.getHp() + "";
                                            myRef.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child(String.valueOf(new1.getCounter())).child("data").setValue(a);
                                            myRef.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child(String.valueOf(new1.getCounter())).child("name").setValue(b);
                                            myRef.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child(String.valueOf(new1.getCounter())).child("HpMax").setValue(c);
                                            myRef.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child(String.valueOf(new1.getCounter())).child("count").setValue(d);
                                            myRef.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child(String.valueOf(new1.getCounter())).child("hp").setValue(e);

//new1.setHp(2);
                                            //new1.setTaskName("rest");

                                        /*    myRef.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child("" +new1.getCounter()).child("data").setValue(tv.getText().toString());
                                            myRef.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child("" +new1.getCounter()).child("name").setValue(ed_in_alertDialog.getText().toString());
                                            myRef.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child("" +  new1.getCounter()).child("HpMax").setValue(editTextDateParam[0]+"");
                                            myRef.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child("" + new1.getCounter()).child("count").setValue(new1.getCounter()+"");
                                            myRef.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child("" +new1.getCounter()).child("hp").setValue(new1.getHp()+"");*/

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                    FirebaseDatabase database2 = FirebaseDatabase.getInstance();
                                    final DatabaseReference myRef2 = database2.getReference();


                                    show.dismiss();
                                    updateUI();
                                }
                            });
                        }
                    });
                    ad.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            // происходит показ статистики
                            final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
                            final View uview = View.inflate(getContext(), R.layout.dialog_statistick, null);
                            builder.setView(uview);
                            final android.support.v7.app.AlertDialog show = builder.show();
                            int hp = new1.getHp();
                            TextView tvk = uview.findViewById(R.id.tvk);
                            TextView tvi = uview.findViewById(R.id.tvi);
                            TextView tvl = uview.findViewById(R.id.tvl);
                            TextView tvo = uview.findViewById(R.id.tvo);

                            pbHorizontal = (ProgressBar) uview.findViewById(R.id.pb_horizontal);
                            tvProgressHorizontal = (TextView) uview.findViewById(R.id.tv_progress_horizontal);

                            Button ok = uview.findViewById(R.id.button6);
                            final String[] k = new String[1];
                            final String[] i = new String[1];
                            final String[] l = new String[1];
                            final String[] o = new String[1];
                            final int[] hpt = new int[1];

                            final int[] HpMax = new int[1];

                            tvk.setText("К "+new1.getK() + " " + new1.getHp() + " " + new1.getHpMax());
                            tvi.setText("И " + new1.getI());
                            tvl.setText("Л " + new1.getL());
                            tvo.setText("О " + new1.getTaskData());
                            //pbHorizontal.setProgress((new1.getHp() / new1.getHpMax()) * 100);
                            progress = (new1.getHp() *100) / new1.getHpMax();
                            postProgress(progress);
                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // происходит закрытие
                                    show.dismiss();
                                }
                            });
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


        private void postProgress(int progress) {
            String strProgress = String.valueOf(progress) + " %";
            pbHorizontal.setProgress(progress);

            if (progress == 0) {
                pbHorizontal.setSecondaryProgress(0);
            } else {
                pbHorizontal.setSecondaryProgress(progress + 3);
            }
            tvProgressHorizontal.setText(strProgress);

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
