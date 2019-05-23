package com.example.p.jumptime;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

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
import java.util.Objects;

public class AddGoal extends Fragment implements TextView.OnEditorActionListener {

    ArrayList<String> arrayStep;
    float fromPosition;
    ViewFlipper flipper;
    EditText editGo;
    EditText tv_goal;
    EditText tv_k;
    EditText tv_i;
    EditText tv_l;
    TextView tv_o;
    String DataEnd;
    String DataBegin;
    Calendar dateAndTime;
    LinearLayout linear;
    private List<View> allEds;
    private ListView mylist;
    private FirebaseUser user;
    private DatabaseReference myRef;
    private FirebaseDatabase database;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_goal, container, false);

        arrayStep = new ArrayList();
        database = FirebaseDatabase.getInstance();
        dateAndTime = Calendar.getInstance();
        DataEnd = "";
        //анимированное перелистование, перехвата жестов
        LinearLayout mainLayout = view.findViewById(R.id.main_layout);
        mainLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // Пользователь нажал на экран, т.е. начало движения
                        // fromPosition - координата по оси X начала выполнения операции
                        fromPosition = event.getX();
                        break;
                    case MotionEvent.ACTION_UP: // Пользователь отпустил экран, т.е. окончание движения
                        float toPosition = event.getX();
                        if (fromPosition > toPosition) {
                            //подключение анимации, плавного перехода
                            flipper.setInAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.go_next_in));
                            flipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.go_next_out));
                            flipper.showNext();
                        } else if (fromPosition < toPosition) {
                            //подключение анимации, плавного перехода
                            flipper.setInAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.go_prev_in));
                            flipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.go_prev_out));
                            flipper.showPrevious();
                        }
                    default:
                        break;
                }
                return true;
            }
        });

        flipper = view.findViewById(R.id.flipper);

        //Создание View и добавление в flipper
        LayoutInflater inflatere = (LayoutInflater) Objects.requireNonNull(getContext()).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int[] layouts = new int[]{R.layout.fragment_add_new_goal_kilo, R.layout.fragment_add_new_goal_category, R.layout.view};
        for (int layout : layouts)
            flipper.addView(inflatere.inflate(layout, null));
        Button bt = view.findViewById(R.id.button5);
        bt.setOnClickListener(new View.OnClickListener() {
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
                        // indexMonth.add(saveInDataBase("month", ed_in_alertDialog.getText().toString()));
                        arrayStep.add(ed_in_alertDialog.getText().toString());
                        updateUI();
                        show.dismiss();

                    }
                });

            }
        });
        tv_goal = view.findViewById(R.id.task_plan);
        tv_k = view.findViewById(R.id.goal_k);
        tv_i = view.findViewById(R.id.goal_i);
        tv_l = view.findViewById(R.id.goal_l);
        tv_o = view.findViewById(R.id.goal_o);
        final DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateAndTime.set(Calendar.YEAR, year);
                dateAndTime.set(Calendar.MONTH, monthOfYear);
                dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                DataEnd = dayOfMonth + "." + monthOfYear + "." + year;
                tv_o.setText(DataEnd);
            }
        };
        tv_o.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), d,
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH))
                        .show();


            }
        });
        setInitializeCurrentDate();
        mylist = view.findViewById(R.id.liststep);
        updateUI();

        ImageView help = view.findViewById(R.id.helpAdmin);
        help.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
                builder.setTitle("Что такое КИЛО?")
                        .setMessage("Для того, чтобы мечты сбывались, их необходимо перевести в форму целей, и упорно трудиться на пути к их достижению. Цель – это определенный результат, который необходимо достичь. Либо вы выбираете цели, либо цели других выбирают вас. Сформулируйте краткую позитивную формулировку цели: ответив на вопросы: что?, сколько?, где?, когда?")
                        .setCancelable(false)
                        .setPositiveButton("закрыть",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        Button addButton = view.findViewById(R.id.button);


        //инициализирован массив с edittext
        allEds = new ArrayList<View>();



        final LinearLayout linear = view.findViewById(R.id.linear);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //находим layout, через него все кнопки и edit тексты, задаем нужные данные
                final View view = getLayoutInflater().inflate(R.layout.custome_edittext, null);
                Button deleteField = view.findViewById(R.id.button2);
                final EditText text = view.findViewById(R.id.editText);
                deleteField.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            //получение родительский view и удаление его же
                            ((LinearLayout) view.getParent()).removeView(view);
                            //удалить эту же запись из массива что бы не оставалось мертвых записей
                            allEds.remove(view);
                        } catch (IndexOutOfBoundsException ex) {
                            ex.printStackTrace();
                        }
                    }
                });

                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
                final View uview = View.inflate(getContext(), R.layout.dialog_new_plan_task, null);
                builder.setView(uview);
                final android.support.v7.app.AlertDialog show = builder.show();

                Button ok = uview.findViewById(R.id.button6);
                final EditText ed_in_alertDialog = uview.findViewById(R.id.editText3);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        text.setText(ed_in_alertDialog.getText().toString());
                        show.dismiss();

                    }
                });

                //добавить созданное в массив
                allEds.add(view);
                //добавить елементы в linearlayout
                linear.addView(view);
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        myRef = database.getReference();
        Button butsav = view.findViewById(R.id.button_save);
        butsav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        myRef.child("users").child(user.getUid()).child("infoUser").child("goal").setValue(tv_goal.getText().toString());
                        myRef.child("users").child(user.getUid()).child("infoUser").child("k").setValue(tv_k.getText().toString());
                        myRef.child("users").child(user.getUid()).child("infoUser").child("i").setValue(tv_i.getText().toString());
                        myRef.child("users").child(user.getUid()).child("infoUser").child("l").setValue(tv_l.getText().toString());
                        myRef.child("users").child(user.getUid()).child("infoUser").child("dataBegin").setValue(DataBegin.toString());
                        myRef.child("users").child(user.getUid()).child("infoUser").child("dataEnd").setValue(DataEnd.toString());

                        String[] items = new String[allEds.size()];
                        myRef.child("users").child(user.getUid()).child("infoUser").child("achiv").child("counter").setValue(allEds.size() + "");

                        //чтение всех елементов этого списка и запись в массив
                        for (int i = 0; i < allEds.size(); i++) {
                            items[i] = ((EditText) allEds.get(i).findViewById(R.id.editText)).getText().toString();


                            myRef.child("users").child(user.getUid()).child("infoUser").child("achiv").child("" + i).setValue(((EditText) allEds.get(i).findViewById(R.id.editText)).getText().toString());
                        }

                        myRef.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child("counter").setValue(arrayStep.size() + "");
                        for (int i = 0; i < arrayStep.size(); i++) {
                            myRef.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child("" + i).setValue(arrayStep.get(i));
                        }
                        Fragment fragment = new Health();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        return view;

    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (editGo.getImeActionId() == EditorInfo.IME_ACTION_NEXT) {

            //кастомный лейаут находим, через него все кнопки и edittext, данные
            @SuppressLint("InflateParams") final View view = getLayoutInflater().inflate(R.layout.custome_edittext, null);
            EditText text = view.findViewById(R.id.editText);

            text.setText("rest");
            //добавляем все что создаем в массив
            allEds.add(view);
            //добавляем елементы в linearlayout
            linear.addView(view);
            return true;
        }

        return false;
    }

    private void setInitializeCurrentDate() {
        // Текущее время
        Date currentDate = new Date();
        // Форматирование времени как "день.месяц.год"
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        DataBegin = dateFormat.format(currentDate);

    }

    private void updateUI() {
        if (getActivity() != null) {
            ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), R.layout.list_text_view, arrayStep);
            mylist.setAdapter(adapter);

        }
    }
}

