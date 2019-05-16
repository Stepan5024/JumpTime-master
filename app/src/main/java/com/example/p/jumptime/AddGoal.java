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
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
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
    ArrayList mBook = new ArrayList();
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
    Calendar dateAndTime = Calendar.getInstance();
    LinearLayout linear;
    private List<View> allEds;
    private ListView lastBook;
    private FirebaseUser user;
    private DatabaseReference myRef;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_goal, container, false);

        // Устанавливаем listener касаний, для последующего перехвата жестов
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
                            flipper.setInAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.go_next_in));
                            flipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.go_next_out));
                            flipper.showNext();
                        } else if (fromPosition < toPosition) {
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

        // Получаем объект ViewFlipper
        flipper = view.findViewById(R.id.flipper);

        // Создаем View и добавляем их в уже готовый flipper
        LayoutInflater inflatere = (LayoutInflater) Objects.requireNonNull(getContext()).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int[] layouts = new int[]{R.layout.fragment_add_new_goal_kilo, R.layout.fragment_add_new_goal_category, R.layout.view};
        for (int layout : layouts)
            flipper.addView(inflatere.inflate(layout, null));
        Button bt = view.findViewById(R.id.button5);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "TAG", Toast.LENGTH_SHORT).show();
            }
        });
        tv_goal = view.findViewById(R.id.goalUser);
        tv_k = view.findViewById(R.id.goal_k);
        tv_i = view.findViewById(R.id.goal_i);
        tv_l = view.findViewById(R.id.goal_l);
        tv_o = view.findViewById(R.id.goal_o);
        final DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateAndTime.set(Calendar.YEAR, year);
                dateAndTime.set(Calendar.MONTH, monthOfYear);
                dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Toast.makeText(getContext(),
                        "year  = " + year + "month " + monthOfYear + " day " + dayOfMonth, Toast.LENGTH_SHORT).show();
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
        lastBook = view.findViewById(R.id.lastBookyou);
        updateUI();
        lastBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {


                if (position + 1 == mBook.size()) {
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
                            mBook.set(mBook.size(), "+");
                            mBook.add(ed_in_alertDialog.getText().toString());
                            updateUI();
                            show.dismiss();
                        }
                    });
                    updateUI();
                } else {
                    //диалог дополнительных шагов
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    final View uview = View.inflate(getContext(), R.layout.dialog_field_punkt, null);
                    builder.setView(uview);
                    final AlertDialog show = builder.show();

                    Button ok = uview.findViewById(R.id.button_goodadd);
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            show.dismiss();
                        }
                    });
                }

              /*  Fragment fragment = new Home();
                FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                fragmentIs = a10;*/
            }
        });
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
        mBook.add("+");
        Button addButton = view.findViewById(R.id.button);


        //инициализировали наш массив с edittext.aьи
        allEds = new ArrayList<View>();

        //находим наш linear который у нас под кнопкой add edittext в activity_main.xml
        final LinearLayout linear = view.findViewById(R.id.linear);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //берем наш кастомный лейаут находим через него все наши кнопки и едит тексты, задаем нужные данные
                final View view = getLayoutInflater().inflate(R.layout.custome_edittext, null);
                Button deleteField = view.findViewById(R.id.button2);
                EditText text = view.findViewById(R.id.editText);
                deleteField.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            //получаем родительский view и удаляем его
                            ((LinearLayout) view.getParent()).removeView(view);
                            //удаляем эту же запись из массива что бы не оставалось мертвых записей
                            allEds.remove(view);
                        } catch (IndexOutOfBoundsException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                text.setText("Some text");
                //добавляем все что создаем в массив
                allEds.add(view);
                //добавляем елементы в linearlayout
                linear.addView(view);
            }
        });

        Button showDataBtn = (Button) view.findViewById(R.id.button3);
        showDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //преобразуем наш ArrayList в просто String Array
                String[] items = new String[allEds.size()];
                //запускаем чтение всех елементов этого списка и запись в массив
                for (int i = 0; i < allEds.size(); i++) {
                    items[i] = ((EditText) allEds.get(i).findViewById(R.id.editText)).getText().toString();

                    //ну и можно сразу же здесь вывести
                   // Log.d("TAG edit text ", ((EditText) allEds.get(i).findViewById(R.id.editText)).getText().toString());
                }
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
                        //преобразуем наш ArrayList в просто String Array
                        String[] items = new String[allEds.size()];
                        myRef.child("users").child(user.getUid()).child("infoUser").child("achiv").child("counter").setValue(allEds.size()+"");

                        //запускаем чтение всех елементов этого списка и запись в массив
                        for (int i = 0; i < allEds.size(); i++) {
                            items[i] = ((EditText) allEds.get(i).findViewById(R.id.editText)).getText().toString();

                            //ну и можно сразу же здесь вывести
                            myRef.child("users").child(user.getUid()).child("infoUser").child("achiv").child(""+i).setValue(((EditText) allEds.get(i).findViewById(R.id.editText)).getText().toString());
                        }
                        Toast.makeText(getContext(),"size trackGoal "+ mBook.size(),Toast.LENGTH_SHORT).show();
                        myRef.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child("counter").setValue(mBook.size()+"");
                        for (int i = 0; i < mBook.size(); i++) {
                            myRef.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child(""+i).setValue(mBook.get(i));
                        }

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
            // обрабатываем нажатие кнопки поиска
            //берем наш кастомный лейаут находим через него все наши кнопки и едит тексты, задаем нужные данные
            final View view = getLayoutInflater().inflate(R.layout.custome_edittext, null);
            EditText text = view.findViewById(R.id.editText);

            text.setText("Some text");
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
            ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), R.layout.list_text_view, mBook);
            lastBook.setAdapter(adapter);

        }
    }
}

