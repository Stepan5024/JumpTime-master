package com.example.p.jumptime;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.ListView;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddGoal extends Fragment implements TextView.OnEditorActionListener {
    ArrayList mBook = new ArrayList();
    float fromPosition;
    ViewFlipper flipper;
    EditText editGo;
    LinearLayout linear;
    private List<View> allEds;
    private ListView lastBook;
    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_goal, container, false);

        // Устанавливаем listener касаний, для последующего перехвата жестов
        LinearLayout mainLayout = (LinearLayout) view.findViewById(R.id.main_layout);
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
        flipper = (ViewFlipper) view.findViewById(R.id.flipper);

        // Создаем View и добавляем их в уже готовый flipper
        LayoutInflater inflatere = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int layouts[] = new int[]{R.layout.fragment_add_new_goal_kilo, R.layout.fragment_add_new_goal_category, R.layout.view};
        for (int layout : layouts)
            flipper.addView(inflatere.inflate(layout, null));
        Button bt = view.findViewById(R.id.button5);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "TAG", Toast.LENGTH_SHORT).show();
            }
        });
        lastBook = view.findViewById(R.id.lastBookyou);
        updateUI();
        lastBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {


                if(position +1 == mBook.size()){
                    Toast.makeText(getContext(), "добавить", Toast.LENGTH_SHORT).show();
                    mBook.add("fff2");
                    updateUI();
                }
                else {
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
        mBook.add("fff");
        Button addButton = (Button) view.findViewById(R.id.button);
        //инициализировали наш массив с edittext.aьи
        allEds = new ArrayList<View>();

        //находим наш linear который у нас под кнопкой add edittext в activity_main.xml
        final LinearLayout linear = (LinearLayout) view.findViewById(R.id.linear);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //берем наш кастомный лейаут находим через него все наши кнопки и едит тексты, задаем нужные данные
                final View view = getLayoutInflater().inflate(R.layout.custome_edittext, null);
                Button deleteField = (Button) view.findViewById(R.id.button2);
                EditText text = (EditText) view.findViewById(R.id.editText);
                deleteField.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            //получаем родительский view и удаляем его
                            ((LinearLayout) view.getParent()).removeView(view);
                            //удаляем эту же запись из массива что бы не оставалось мертвых записей
                            allEds.remove(view);
                        } catch(IndexOutOfBoundsException ex) {
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

        return view;

    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (editGo.getImeActionId() == EditorInfo.IME_ACTION_NEXT) {
            // обрабатываем нажатие кнопки поиска
            //берем наш кастомный лейаут находим через него все наши кнопки и едит тексты, задаем нужные данные
            final View view = getLayoutInflater().inflate(R.layout.custome_edittext, null);
            Button deleteField = (Button) view.findViewById(R.id.button2);
            EditText text = (EditText) view.findViewById(R.id.editText);
            text.setText("Some text");
            //добавляем все что создаем в массив
            allEds.add(view);
            //добавляем елементы в linearlayout
            linear.addView(view);
            return true;
        }

        return false;
    }

    public void updateUI() {
        if (getActivity() != null) {



           ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.list_text_view, mBook);
            lastBook.setAdapter(adapter);
        }
    }
}

