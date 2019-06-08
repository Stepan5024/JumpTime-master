package com.example.p.jumptime.Fragment;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.p.jumptime.Controller.DataBase;
import com.example.p.jumptime.Model.ItemDetails;
import com.example.p.jumptime.Controller.ItemListBaseAdapter;
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
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Objects;

/*
 * Основная задача создание новых дел и сохранение в БД SQLite в таблице
 *
 * */
public class AddTask extends Fragment {

    Calendar dateAndTime;
    TextView priority;
    TextView reminder;
    View rootView;
    String timeTask;
    Button add;
    TextView EndDateTime;
    EditText ValueView;
    Spinner spinner_priority;
    int selected_priority = 0;
    final String LOG_TAG = "RESULT";
    //в нем храняться названия шагов-планов пользователя
    ArrayList<String> list = new ArrayList();
    //служит для хранения всей информации шагов
    ArrayList<TaskForStep> step = new ArrayList();
    String dataTask;
    FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser user;
    Spinner spinner_category;
    int[] counter = {1};
    String active_name;
    ItemDetails selected_icon_hero = new ItemDetails();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_add_task, container, false);


        priority = rootView.findViewById(R.id.priority);
        initElements();
        initHeroIc();
        reminder = rootView.findViewById(R.id.remind);
        EndDateTime = rootView.findViewById(R.id.time);
        ValueView = rootView.findViewById(R.id.ValueView);
        TextView selectHero = rootView.findViewById(R.id.remind);

        selectHero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //появляется диалоговое окно с изображениями для возможности поставить метку, если не выбрана - default icon app
                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
                final View uview = View.inflate(getContext(), R.layout.dialog_select_icon, null);
                builder.setView(uview);
                final android.support.v7.app.AlertDialog show = builder.show();

                ArrayList<ItemDetails> image_details = GetSearchResults();

                final ListView lv1 = (ListView) uview.findViewById(R.id.listview);
                lv1.setAdapter(new ItemListBaseAdapter(getContext(), image_details));

                lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                        Object o = lv1.getItemAtPosition(position);
                        ItemDetails obj_itemDetails = (ItemDetails) o;
                        selected_icon_hero = obj_itemDetails;
                        Toast.makeText(getContext(), "Выбор : " + ' ' + obj_itemDetails.getName() + ". Ответ сохранен", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });



        spinner_priority = (Spinner) rootView.findViewById(R.id.spinner_priority);
        spinner_category = (Spinner) rootView.findViewById(R.id.spinner_category);
        // получение листа шагов для отображения  spinner_category  в качестве Категории
        setStep();


        spinner_priority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                String[] choose = getResources().getStringArray(R.array.list_for_priority);
                if (choose[selectedItemPosition].compareTo("Не важно") == 0) {
                    selected_priority = 0;
                } else if (choose[selectedItemPosition].compareTo("Среднее") == 0) {
                    selected_priority = 1;
                } else if (choose[selectedItemPosition].compareTo("Важно") == 0) {
                    selected_priority = 2;
                } else {
                    selected_priority = 3;
                }


            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        add = (Button) rootView.findViewById(R.id.addBut);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ValueView.getText().toString().compareTo("") != 0) {
                    DataBase.DBHelper dbHelper = new DataBase.DBHelper(getContext());
                    // подключаемся к БД

                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    ContentValues cv = new ContentValues();
                    Log.d(LOG_TAG, "--- Insert in mytable: ---");
                    // подготовим данные для вставки в виде пар: наименование столбца -
                    // значение
                    cv.put("name", ValueView.getText().toString());
                    cv.put("data", dataTask);
                    cv.put("time", timeTask);
                    cv.put("k", "k");
                    cv.put("i", "i");
                    cv.put("l", "l");
                    cv.put("o", "o");
                    cv.put("priority", selected_priority);
                    cv.put("project", selected_icon_hero.getImageNumber() + "");
                    cv.put("active", "1");
                    // вставляем запись и получаем ее ID
                    long rowID = db.insert("mytable", null, cv);
                    Log.d(LOG_TAG, "row inserted, ID = " + rowID);
                }
                ValueView.setText("");
                addToHp(active_name);
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

                    dataTask = dayOfMonth + "." + (monthOfYear + 1) + "." + year;
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

    void initElements(){
        Calendar c = new GregorianCalendar();
        dateAndTime = Calendar.getInstance();
        timeTask = c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE);
        // Текущее время
        Date currentDate = new Date();
        // Форматирование времени как "день.месяц.год"
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        dataTask = dateFormat.format(currentDate);

    }
    //добавляет hp к выбранному шагу
    private void addToHp(String name) {
        for (int i = 0; i < list.size(); i++) {
            if (step.get(i).getTaskName().compareTo(name) == 0) {

                step.get(i).setHp(step.get(i).getHp() + 1);
                String a = step.get(i).getTaskData();
                String b = step.get(i).getName();
                String c = step.get(i).getHpMax() + "";
                String d = step.get(i).getCounter() + "";
                String e = (step.get(i).getHp()) + "";
                myRef.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child(String.valueOf(step.get(i).getCounter() - 1)).child("data").setValue(a);
                myRef.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child(String.valueOf(step.get(i).getCounter() - 1)).child("name").setValue(b);
                myRef.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child(String.valueOf(step.get(i).getCounter() - 1)).child("HpMax").setValue(c);
                myRef.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child(String.valueOf(step.get(i).getCounter() - 1)).child("count").setValue(d);
                myRef.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child(String.valueOf(step.get(i).getCounter() - 1)).child("hp").setValue(e);


            }
        }

    }
    void initHeroIc(){
        selected_icon_hero.setImageNumber(0);
        selected_icon_hero.setName("default");
        selected_icon_hero.setItemDescription("");
        selected_icon_hero.setPrice("");
    }
    private void setStep() {

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    counter[0] = Integer.valueOf(dataSnapshot.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child("counter").getValue(String.class));
                    for (int i = 1; i < counter[0] + 1; i++) {

                        String data = dataSnapshot.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child("" + i).child("data").getValue(String.class);
                        String name = dataSnapshot.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child("" + i).child("name").getValue(String.class);
                        String k = dataSnapshot.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child("" + i).child("k").getValue(String.class);
                        String ki = dataSnapshot.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child("" + i).child("i").getValue(String.class);
                        String l = dataSnapshot.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child("" + i).child("l").getValue(String.class);

                        int HpMax = Integer.valueOf(dataSnapshot.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child("" + i).child("HpMax").getValue(String.class));
                        int hp = Integer.valueOf(dataSnapshot.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child("" + i).child("hp").getValue(String.class));
                        int count = Integer.valueOf(dataSnapshot.child("users").child(user.getUid()).child("infoUser").child("trackGoal").child("" + i).child("count").getValue(String.class));
                        step.add(new TaskForStep(name, data, k, ki, l, HpMax, hp, count, getActivity()));
                        list.add(name);
                        active_name = "";
                        // Создаем адаптер ArrayAdapter spinner_category
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
                        // Определяем разметку для использования при выборе элемента
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Применяем адаптер
                        spinner_category.setAdapter(adapter);

                        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> parent,
                                                       View itemSelected, int position, long selectedId) {
                                //  Toast.makeText(getContext(), "er1 r" + position, Toast.LENGTH_SHORT).show();
                                for (int i = 0; i < list.size(); i++) {


                                    if (list.get(i).compareTo(parent.getItemAtPosition(position).toString()) == 0) {
                                        //   Toast.makeText(getContext(), "er1 " + list.get(i), Toast.LENGTH_SHORT).show();
                                        active_name = list.get(i);

                                    }
                                }

                            }

                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                    }
                } catch (NumberFormatException ex) {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    // создается лист для выбора метки с изображением, названием, описанием
    // сейчас работаю над рандомным первоначальным выбором 4 персонажей, которые будут служить как метки для дел
    private ArrayList<ItemDetails> GetSearchResults() {
        ArrayList<ItemDetails> results = new ArrayList<ItemDetails>();
        ItemDetails item_details = new ItemDetails();

        item_details = new ItemDetails();
        item_details.setName("Элион");
        item_details.setItemDescription("Больше всех я ненавижу свою совесть: зараза, ворчит постоянно. И еще силу воли — эта вообще где-то шляется");
        item_details.setPrice("С каждым делом становиться терпеливее, что тебе тоже нужно");
        item_details.setImageNumber(1);
        results.add(item_details);

        item_details = new ItemDetails();
        item_details.setName("Огава");
        item_details.setItemDescription("Характер у меня замечательный, только нервы у всех какие-то слабые.");
        item_details.setPrice("Если план «А» не сработал, то у тебя есть еще 32 буквы, чтобы попробовать.");
        item_details.setImageNumber(2);
        results.add(item_details);

        item_details = new ItemDetails();
        item_details.setName("Пингвиненок Гвиночка");
        item_details.setItemDescription("Fish Hunter - готова проводить под водой в поисках рыбы очень долгое время. Бери пример!");
        item_details.setPrice("Упорство и терпение - вся рыба твоя");
        item_details.setImageNumber(3);
        results.add(item_details);

        item_details = new ItemDetails();
        item_details.setName("Тоторо");
        item_details.setItemDescription("Обычно Тоторо невидим для людей. Днём большей частью спит, а в лунные вечера любит играть на окарине. Также способен летать");
        item_details.setPrice("Я, конечно, не совершенство. Но шедевр еще тот!");
        item_details.setImageNumber(4);
        results.add(item_details);

        item_details = new ItemDetails();
        item_details.setName("Чернушка");
        item_details.setItemDescription("Пугливый, робкое создание, которое живёт в заброшенных домах и покрывают их пылью и сажей. Чёрный и пушистый комочек сажи поможет \"раздавить\" что угодно");
        item_details.setPrice("У меня прекрасная работа, отличный город, уютная квартира и шикарно подобранные камушки");
        item_details.setImageNumber(5);
        results.add(item_details);


        item_details = new ItemDetails();
        item_details.setName("Котобус");
        item_details.setItemDescription("Гибрид автобуса и кошки. У него 12 лап. Готов вместить и перевести с ветерком вашу задачу");
        item_details.setPrice("Как бы я ни обходил любые препятсвия - все равно влечу куда-нибудь");
        item_details.setImageNumber(6);
        results.add(item_details);

        item_details.setName("Кот Зизи");
        item_details.setItemDescription("Черный котёнок Зизи очень очень активный, шаловлив и прыток. Обожает новые приключения");
        item_details.setPrice("С эстетической точки зрения, в мире существует только две идеальные вещи: часы и кошки. ");
        item_details.setImageNumber(7);


        return results;
    }
}



